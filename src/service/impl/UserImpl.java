package service.impl;

import data.User;
import service.sql.MySQLConnection;
import service.UserService;

import java.sql.SQLException;

public class UserImpl extends MySQLConnection implements UserService {
    @Override
    public User selectByName(String name) {
        String sql = "select * from users where name = '" + name + "'";
        try {
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"));
            } else {
                return new User();
            }

        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return new User();
        }
    }
}
