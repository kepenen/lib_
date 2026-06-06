package service;

import data.User;

import java.sql.SQLException;

public interface UserService {
    User selectByName(String name);
}
