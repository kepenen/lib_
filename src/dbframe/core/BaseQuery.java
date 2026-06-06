package dbframe.core;


import dbframe.handler.ResultSetHandler;
import java.sql.*;
import java.util.List;

public class BaseQuery {
    /**
     * 通用查询
     * @param sql 带?占位符SQL
     * @param params 占位符参数数组
     * @param rsh 结果处理器
     */
    public <T> T query(String sql, List<SqlParam> params, ResultSetHandler<T> rsh){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        T res=null;
        try{
            conn=DBUtil.getConn();
            pstmt=conn.prepareStatement(sql);
            // 循环赋值占位符
            for(int i=0;i<params.size();i++){
                SqlParam param = params.get(i);
                pstmt.setObject(i+1,param.getValue(),param.getType());
            }
            rs=pstmt.executeQuery();
            res=rsh.handle(rs);
        }catch(Exception e){e.printStackTrace();}finally{
            DBUtil.close(conn,pstmt,rs);
        }
        return res;
    }

    // 增删改通用方法
    public int update(String sql,List<SqlParam> params){
        Connection conn=null;
        PreparedStatement pstmt=null;
        int rows=0;
        try{
            conn=DBUtil.getConn();
            pstmt=conn.prepareStatement(sql);
            for(int i=0;i<params.size();i++){
                SqlParam param = params.get(i);
                pstmt.setObject(i+1,param.getValue(),param.getType());
            }
            rows=pstmt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}finally{
            DBUtil.close(conn,pstmt);
        }
        return rows;
    }
}
