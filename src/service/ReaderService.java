package service;

import data.Reader;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.BeanHandler;
import dbframe.handler.ListHandler;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static service.Util.deleteIds;

public class ReaderService {
    private final BaseQuery base;
    List<SqlParam> params;

    public ReaderService()
    {
        this.base = new BaseQuery();
    }

    public List<Reader> select() {
        String sql = "select * from readers";
        return base.query(sql, new ArrayList<>(), new ListHandler<>(Reader.class));
    }

    public int insert(Reader reader) {
        String sql = "insert into readers(username, password, name, stu_id, stu_class, phone) values(?,?,?,?,?,?)";
        setParamsNoId(reader);
        return base.update(sql, params);
    }

    public int update(Reader reader) {
        String sql = "update readers set username=?, password=?, name = ?, stu_id = ?, stu_class = ?, phone = ? where id = ?";
        setParamsNoId(reader);
        params.add(new SqlParam(reader.getId(), Types.VARCHAR));
        return base.update(sql, params);
    }

    public int deleteReaders(Long[] ids) {
        StringBuilder sql = new StringBuilder("delete from readers where id in (");
        return deleteIds(ids, sql, base);
    }

    private void setParamsNoId(Reader reader) {
        params = new ArrayList<>();
        params.add(new SqlParam(reader.getUsername(), Types.VARCHAR));
        params.add(new SqlParam(reader.getPassword(), Types.VARCHAR));
        params.add(new SqlParam(reader.getName(), Types.VARCHAR));
        params.add(new SqlParam(reader.getStu_id(), Types.VARCHAR));
        params.add(new SqlParam(reader.getStu_class(), Types.VARCHAR));
        params.add(new SqlParam(reader.getPhone(), Types.VARCHAR));
    }
    public Reader selectByUsername(String username) {
        String sql = "select * from readers where username = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(username, Types.VARCHAR));
        return base.query(sql, params, new BeanHandler<>(Reader.class));
    }

}
