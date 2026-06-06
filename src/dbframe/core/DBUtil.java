package dbframe.core;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DBUtil {
    private static String driver,url,user,pwd;
    static{
        try{
            Properties prop = new Properties();
            InputStream is = DBUtil.class.getResourceAsStream("/db.properties");
            prop.load(is);
            driver=prop.getProperty("driver");
            url=prop.getProperty("url");
            user=prop.getProperty("user");
            pwd=prop.getProperty("pwd");
            Class.forName(driver);
        }catch(Exception e){e.printStackTrace();}
    }
    // 获取连接
    public static Connection getConn() throws SQLException{
        return DriverManager.getConnection(url,user,pwd);
    }
    // 关闭资源重载
    public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs){
        try{if(rs!=null)rs.close();}catch(SQLException e){}
        try{if(pstmt!=null)pstmt.close();}catch(SQLException e){}
        try{if(conn!=null)conn.close();}catch(SQLException e){}
    }
    public static void close(Connection conn,PreparedStatement pstmt){
        close(conn,pstmt,null);
    }
}
