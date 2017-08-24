package spider.provider;

import common.util.DbUtil;
import model.news.TNews;
import model.newsarea.TNewsArea;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
public class NewsProvider {

    private static Logger logger = LoggerFactory.getLogger(NewsProvider.class);
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/van_news?characterEncoding=utf8&useSSL=false";
    private static String user = "root";
    private static String password = "root";

    public static int AddNews(TNews model) {
        int result = 0;
        if (model != null && !model.getTitle().equals("")) {
            String strTitle = model.getTitle().replace("?", "？");
            String STR_SQL_FIND_TITLE = "SELECT id from t_news where title ='" + strTitle + "' limit 1";
            Connection conn = new DbUtil(driverClassName, url, user, password).getConnection();
            QueryRunner queryRunner = new QueryRunner();
            try {
                List<TNews> list = queryRunner.query(conn, STR_SQL_FIND_TITLE, new BeanListHandler<TNews>(TNews.class));
                if (list == null || list.size() == 0) {
                    String STR_SQL_ADD = "INSERT INTO `van_news`.`t_news`\n" +
                            "(`title`,\n" +
                            "`digest`,\n" +
                            "`pic`,\n" +
                            "`source`,\n" +
                            "`tags`,\n" +
                            "`type`,\n" +
                            "`category_id`,\n" +
                            "`category_name`,\n" +
                            "`content`,\n" +
                            "`count_comment`,\n" +
                            "`count_like`,\n" +
                            "`count_browser`,\n" +
                            "`publish_time`,\n" +
                            "`status`,\n" +
                            "`city_id`,\n" +
                            "`city_area_id`,\n" +
                            "`city_name`,\n" +
                            "`source_docid`,\n" +
                            "`source_website`,\n" +
                            "`source_url`,\n" +
                            "`check_time`,\n" +
                            "`createdtime`)\n" +
                            "VALUES\n" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);\n";
                    Object[] params = {model.getTitle(), model.getDigest(), model.getPic(), model.getSource(), model.getTags(),
                            model.getType(), model.getCategoryId(), model.getCategoryName(), model.getContent(), model.getCountComment(),
                            model.getCountLike(), model.getCountBrowser(), model.getPublishTime(), model.getStatus(),
                            model.getCityId(), model.getCityAreaId(), model.getCityName(),
                            model.getSourceDocid(), model.getSourceWebsite(), model.getSourceUrl(),
                            model.getCheckTime(), model.getCreatedtime()};
                    try {
                        result = queryRunner.update(conn, STR_SQL_ADD, params);
                        DbUtils.closeQuietly(conn);
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                    } finally {
                        DbUtils.closeQuietly(conn);
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        return result;
    }

    public static boolean ExistNewsArea(String area_id) {
        boolean result = false;
        String sql = "SELECT id from t_news_area where status=1 and area_id ='" + area_id + "' limit 1";
        Connection conn = new DbUtil(driverClassName, url, user, password).getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            List<TNewsArea> list = queryRunner.query(conn, sql, new BeanListHandler<TNewsArea>(TNewsArea.class));
            if (list == null || list.size() == 0) {
                result = false;
            } else {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return result;
    }

    public static int AddNewsArea(TNewsArea model) {
        int result = 0;
        String STR_SQL_ADD = "INSERT INTO `van_news`.`t_news_area`\n" +
                "(`area_id`,\n" +
                "`name`,\n" +
                "`first_letter`,\n" +
                "`parent_id`,\n" +
                "`status`,\n" +
                "`createdtime`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?);\n";
        Object[] params = {model.getAreaId(), model.getName(), model.getFirstLetter(), model.getParentId(), model.getStatus(), model.getCreatedtime()};
        result = ExecInsert(STR_SQL_ADD, params);
        return result;
    }

    public static List<TNewsArea> ListNewsAreaAll() {
        String sql = "SELECT `id`, `area_id` as `areaid`, `name`, `first_letter` as `firstletter`, `parent_id` as `parentid`, `status`, `createdtime` from t_news_area  ;";
        return ListNewsArea(sql);
    }

    public static List<TNewsArea> ListNewsAreaUsable() {
        String sql = "SELECT `id`, `area_id` as `areaid`, `name`, `first_letter` as `firstletter`, `parent_id` as `parentid`, `status`, `createdtime` from t_news_area where `status`=1 ;";
        return ListNewsArea(sql);
    }

    private static List<TNewsArea> ListNewsArea(String sql) {
        List<TNewsArea> result = null;
        Connection conn = new DbUtil(driverClassName, url, user, password).getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            result = queryRunner.query(conn, sql, new BeanListHandler<>(TNewsArea.class));
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return result;
    }

//    private static <T> List<T> ExecQuery(Class<T> tClass,String sql,Object[] params) {
//        int result = 0;
//        Connection conn = new DbUtil(driverClassName, url, user, password).getConnection();
//        QueryRunner queryRunner = new QueryRunner();
//        try {
//            result = queryRunner.query(conn, sql, params);
//            DbUtils.closeQuietly(conn);
//        } catch (SQLException e) {
//            logger.error(e.getMessage());
//        } finally {
//            DbUtils.closeQuietly(conn);
//        }
//        return result;
//    }

    private static int ExecInsert(String sql, Object[] params) {
        int result = 0;
        Connection conn = new DbUtil(driverClassName, url, user, password).getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            result = queryRunner.update(conn, sql, params);
            DbUtils.closeQuietly(conn);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return result;
    }
}
