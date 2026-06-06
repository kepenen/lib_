package service.impl;

import data.Genre;
import service.GenreService;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.ListHandler;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class GenreImpl implements GenreService {
    private final BaseQuery base;

    public GenreImpl() {
        base = new BaseQuery();
    }

    @Override
    public List<Genre> select() {
        String sql = "select * from genres";
        return base.query(sql, new ArrayList<>(), new ListHandler<>(Genre.class));
    }

    @Override
    public int add(Genre g) {
        String sql = "insert into genres(genre) values (?)";
        List<SqlParam> params = new ArrayList<>();
        params.add(new SqlParam(g.getGenre(), Types.VARCHAR));
        return base.update(sql, params);
    }

    @Override
    public int delete(Genre g) {
        String sql = "delete from genres where genre_id=?";
        List<SqlParam> params = new ArrayList<>();
        params.add(new SqlParam(g.getId(), Types.INTEGER));
        return base.update(sql, params);
    }

    @Override
    public int update(Genre g) {
        String sql = "update genres set genre=? WHERE id=?";
        List<SqlParam> params = new ArrayList<>();
        params.add(new SqlParam(g.getGenre(), Types.VARCHAR));
        params.add(new SqlParam(g.getId(), Types.INTEGER));
        return base.update(sql, params);
    }
}
