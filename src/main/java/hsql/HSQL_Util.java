package hsql;


import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 方便单机程序使用HSQL的工具类，包括启动，关闭，连接。数据库默认不加密，用户为sa，密码空
 *
 * @author 郑高强
 */
public class HSQL_Util {

    public static final int PORT = 9002;
    public static final String DB_NAME = "kenko";       //数据库文件名，同时也是本类中的数据库名
    public static final String DB_PATH = "./db/";
    public static final String USER_NAME = "sa";
    public static final String PASSWORD = "";
    public static final int SERVER_MODE = 0;
    public static final int STAND_ALONE_MODE = 1;   //In-Process
    public static int mode = SERVER_MODE;          //记录当前用什么模式，开发时用Server，发布时用standalone

    /**
     * 启动数据库服务
     */
    public static boolean startHSQL() {
        if (mode == SERVER_MODE) {
            Server server = new Server();//它可是hsqldb.jar里面的类啊。
            server.setDatabaseName(0, DB_NAME);
            server.setDatabasePath(0, DB_PATH + DB_NAME);
            server.setPort(PORT);
            server.setSilent(true);
            server.start();         //自动多线程运行
            System.out.println("hsqldb started...");
        } else if (mode == STAND_ALONE_MODE) {
            //standalone模式，打开连接就同时启动数据库，所以这里可以什么都不做
        }

        try {
            Thread.sleep(800);        // 等待Server启动
        } catch (InterruptedException e) {
        }
        return true;
    }

    /**
     * 关闭数据库服务
     */
    public static boolean stopHSQL() {
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate("SHUTDOWN;");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(HSQL_Util.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * 获取连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            if (mode == SERVER_MODE) {
                conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:" + PORT + "/" + DB_NAME, USER_NAME, PASSWORD);
                //                conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", USER_NAME, PASSWORD);
            } else if (mode == STAND_ALONE_MODE) {
                conn = DriverManager.getConnection("jdbc:hsqldb:file:" + DB_PATH + DB_NAME, USER_NAME, PASSWORD);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HSQL_Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HSQL_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws ServletException, SQLException {
        HSQL_Util.mode = HSQL_Util.SERVER_MODE;
        //        HSQL_Util.startHSQL();
        //        Connection conn = HSQL_Util.getConnection();
        //        createTable();


        queryData(getConnection());
        //        HSQL_Util.stopHSQL();

        //        init();
    }

    private static void createTable() {
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate("create table customer(id integer not null primary key,firstname varchar(20),lastname varchar(20))");
            for (int i = 10; i < 20; i++) {
                statement.executeUpdate("insert into customer values(" + i + ",'liu','zhaoyang')");
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(HSQL_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void queryData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * F FROM CUSTOMER");
        int col = rs.getMetaData().getColumnCount();
        System.out.println("============================");
        while (rs.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(rs.getString(i) + "\t");
                if ((i == 2) && (rs.getString(i).length() < 8)) {
                    System.out.print("\t");
                }
            }
            System.out.println("");
        }
        System.out.println("============================");
    }


    public static void init() throws ServletException {
        try {
            System.out.println("Starting Database");
            HsqlProperties p = new HsqlProperties();
            p.setProperty("server.database.0", "file:/opt/db/crm");
            p.setProperty("server.dbname.0", "mydb");
            p.setProperty("server.port", "9001");
            Server server = new Server();
            server.setProperties(p);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();
        } catch (ServerAcl.AclFormatException afex) {
            throw new ServletException(afex);
        } catch (IOException ioex) {
            throw new ServletException(ioex);
        }
    }
}