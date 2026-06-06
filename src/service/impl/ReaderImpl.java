package service.impl;

import data.Reader;
import service.ReaderService;
import service.sql.MySQLConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderImpl extends MySQLConnection implements ReaderService {

    @Override
    public List<Reader> select() {
        String sql = "select * from readers";
        List<Reader> readers = new ArrayList<>();
        try {
            rs = stmt.executeQuery(sql);
            if (rs == null) {
                return readers;
            }
            while (rs.next()) {
                readers.add(new Reader(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("stu_id"),
                        rs.getString("stu_class"),
                        rs.getString("phone")));
            }
        } catch (SQLException e) {
            showDialog(this.toString(), e);
        }
        return readers;
    }



}
