package dbframe.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class BeanHandler<T> implements ResultSetHandler<T>{
    private Class<T> clazz;
    public BeanHandler(Class<T> clazz){this.clazz=clazz;}
    @Override
    public T handle(ResultSet rs) throws SQLException {
        if(!rs.next()) return null;
        try{
            T obj = clazz.newInstance();
            ResultSetMetaData meta = rs.getMetaData();
            // 反射给属性赋值（字段名=属性名）
            for(int i=1;i<=meta.getColumnCount();i++){
                String colName = meta.getColumnName(i);
                Object val = rs.getObject(i);
                // 反射setter方法
                java.lang.reflect.Method set = clazz.getDeclaredMethod("set"+upperFirst(colName),val.getClass());
                set.invoke(obj,val);
            }
            return obj;
        }catch(Exception e){throw new SQLException(e);}
    }
    private String upperFirst(String s){
        return Character.toUpperCase(s.charAt(0))+s.substring(1);
    }
}

