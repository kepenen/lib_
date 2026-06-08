package service;

import data.User;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.BeanHandler;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final BaseQuery base;

    public UserService() {
        this.base = new BaseQuery();
    }

    public User selectByName(String name) {
        String sql = "select * from users where name=?";
        List<SqlParam> params = new ArrayList<>();
        params.add(new SqlParam(name, Types.VARCHAR));
        return base.query(sql, params, new BeanHandler<>(User.class));
    }
}
