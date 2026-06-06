package service;

import data.Borrow;
import data.Reader;

import java.sql.SQLException;
import java.util.List;

public interface BorrowerService {
    void select() throws SQLException;
    int add(Borrow b) throws SQLException;
    int delete(Borrow b) throws SQLException;
    int update(Borrow o, Borrow n) throws SQLException;
}
