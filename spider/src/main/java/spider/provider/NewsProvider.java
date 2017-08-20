package spider.provider;

import common.util.DbUtil;
import model.news.TNews;
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
                            "`pic`,\n" +
                            "`source`,\n" +
                            "`tags`,\n" +
                            "`type`,\n" +
                            "`content`,\n" +
                            "`count_comment`,\n" +
                            "`count_like`,\n" +
                            "`count_browser`,\n" +
                            "`publish_time`,\n" +
                            "`check_time`,\n" +
                            "`status`,\n" +
                            "`createdtime`)\n" +
                            "VALUES\n" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?);\n";
                    Object[] params = {model.getTitle(), model.getPic(), model.getSource(), model.getTags(),
                            model.getType(), model.getContent(), model.getCountComment(), model.getCountLike(), model.getCountBrowser(),
                            model.getPublishTime(), model.getCheckTime(), model.getStatus(), model.getCreatedtime()};
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
}
