package service.impl;

import data.User;
import service.UserService;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.BeanHandler;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements UserService {
    private final BaseQuery base;

    public UserImpl() {
        this.base = new BaseQuery();
    }

    @Override
    public User selectByName(String name) {
        String sql = "select * from users where name=?";
        List<SqlParam> params = new ArrayList<>();
        params.add(new SqlParam(name, Types.VARCHAR));
        return base.query(sql, params, new BeanHandler<>(User.class));
    }
}
