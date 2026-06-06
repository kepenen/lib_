package service.impl;

import data.Reader;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.ListHandler;
import service.ReaderService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static service.impl.ImplUtil.deleteIds;

public class ReaderImpl implements ReaderService {
    private final BaseQuery base;
    List<SqlParam> params;

    public ReaderImpl()
    {
        this.base = new BaseQuery();
    }

    @Override
    public List<Reader> select() {
        String sql = "select * from readers";
        return base.query(sql, new ArrayList<>(), new ListHandler<>(Reader.class));
    }
    @Override
    public int insert(Reader reader) {
        String sql = "insert into readers(name, stu_id, stu_class, phone) values(?,?,?,?)";
        setParamsNoId(reader);
        return base.update(sql, params);
    }
    @Override
    public int update(Reader reader) {
        String sql = "update readers set  name = ?, stu_id = ?, stu_class = ?, phone = ? where id = ?";
        setParamsNoId(reader);
        params.add(new SqlParam(reader.getId(), Types.VARCHAR));
        return base.update(sql, params);
    }
    @Override
    public int deleteReaders(Long[] ids) {
        StringBuilder sql = new StringBuilder("delete from readers where id in (");
        return deleteIds(ids, sql, base);
    }

    private void setParamsNoId(Reader reader) {
        params = new ArrayList<>();
        params.add(new SqlParam(reader.getName(), Types.VARCHAR));
        params.add(new SqlParam(reader.getStu_id(), Types.VARCHAR));
        params.add(new SqlParam(reader.getStu_class(), Types.VARCHAR));
        params.add(new SqlParam(reader.getPhone(), Types.VARCHAR));
    }

}
