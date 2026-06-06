package service.sql;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class MySQLConnection extends SQLExceptionHandle{
    private static java.sql.Connection conn;
    public static Statement stmt;
    public static ResultSet rs;

    public static void init() {
        String url = "jdbc:mysql://127.0.0.1/libtest?useSSL=true&serverTimeZone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "4207876");
            stmt = conn.createStatement();
        }  catch (Exception e) {
            showDialog("SQLExceptionHandle.init", e);
        }

    }

    public static void close(){
        try {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        } catch (Exception e) {
            showDialog("SQLExceptionHandle.close", e);
        }

    }

}
