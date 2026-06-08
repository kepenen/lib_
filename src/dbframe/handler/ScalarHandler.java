package dbframe.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScalarHandler<T> implements ResultSetHandler<T>{
    @Override
    public T handle(ResultSet rs) throws SQLException {
        return rs.next()?(T)rs.getObject(1):null;
    }
}
