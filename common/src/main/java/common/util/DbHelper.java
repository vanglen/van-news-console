package common.util;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with Administrator
 * DATE:2016/12/22
 * Time:13:23
 */
public class DbHelper {

    private static Logger logger = LoggerFactory.getLogger(DbHelper.class);


    private static String driverClassName = Common.getConfigDB("jdbc.driver");
    private static String url = Common.getConfigDB("jdbc.url");
    private static String user = Common.getConfigDB("jdbc.username");
    private static String password = Common.getConfigDB("jdbc.password");

    /**
     * 获取数据库链接
     *
     * @return 数据库链接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            DbUtils.loadDriver(driverClassName);
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            logger.error("数据连接失败:" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
