package service.impl;

import data.Book;
import service.BookService;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.ListHandler;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BookImpl implements BookService {
    private final BaseQuery base;
    List<SqlParam> params;
    public BookImpl() {
        base = new BaseQuery();
    }

    @Override
    public List<Book> selectByGenreID(Long genre_id) {
        // SELECT id, title, isbn, author, num, genre_id FROM books WHERE genre_id =?
        String s = "select id, title, isbn, author, num, genre_id, (select count(*) from borrow where books.id = borrow.book_id) as count_borrowed from books where genre_id =?";
        List<SqlParam> params = new ArrayList<>();
        params.add(new SqlParam(genre_id, Types.INTEGER));
        return base.query(s, params, new ListHandler<>(Book.class));
    }

    @Override
    public int insert(Book b) {
        String sql = "INSERT INTO books(title, isbn, author, num, genre_id) VALUES (?,?,?,?,?)";
        setParamsNoId(b);
        return base.update(sql,  params);
    }

    @Override
    public int deleteBooks(Long[] ids) {
        StringBuilder sql = new StringBuilder("delete from books where id in (");
        return ImplUtil.deleteIds(ids, sql, base);
    }

    @Override
    public int update(Book b) {
        String s = "UPDATE books SET title=?, isbn=?, author=?, num=?,genre_id=? where id=?";
        setParamsNoId(b);
        params.add(new SqlParam(b.getId(), Types.INTEGER));
        return base.update(s, params);
    }

    private void setParamsNoId(Book b) {
        params = new ArrayList<>();
        params.add(new SqlParam(b.getTitle(), Types.VARCHAR));
        params.add(new SqlParam(b.getIsbn(), Types.VARCHAR));
        params.add(new SqlParam(b.getAuthor(), Types.VARCHAR));
        params.add(new SqlParam(b.getNum(), Types.INTEGER));
        params.add(new SqlParam(b.getGenre_id(), Types.INTEGER));
    }
}
