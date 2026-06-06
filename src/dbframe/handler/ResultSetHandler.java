package dbframe.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

// 顶层接口：不同返回格式实现不同处理
public interface ResultSetHandler<T> {
    T handle(ResultSet rs) throws SQLException;
}