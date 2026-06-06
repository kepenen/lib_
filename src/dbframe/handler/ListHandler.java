package dbframe.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListHandler<T> implements ResultSetHandler<java.util.List<T>>{
    private Class<T> clazz;
    public ListHandler(Class<T> clazz){this.clazz=clazz;}
    @Override
    public java.util.List<T> handle(ResultSet rs) throws SQLException {
        java.util.List<T> list = new java.util.ArrayList<>();
        BeanHandler<T> bh = new BeanHandler<>(clazz);
        T bean;
        while((bean=bh.handle(rs))!=null){
            list.add(bean);
        }
        return list;
    }
}
