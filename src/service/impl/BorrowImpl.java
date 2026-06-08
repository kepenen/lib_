package service.impl;

import data.Book;
import data.Borrow;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.BeanHandler;
import dbframe.handler.ListHandler;
import service.BorrowService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BorrowImpl implements BorrowService {
    private final BaseQuery base;
    List<SqlParam> params;

    public BorrowImpl() {
        base = new BaseQuery();
    }

    @Override
    public int insert(Borrow borrow) {
        String sql = "insert into borrow(reader_id, book_id) values (?,?)";
        params = new ArrayList<>();
        params.add(new SqlParam(borrow.getReader_id(), Types.INTEGER));
        params.add(new SqlParam(borrow.getBook_id(), Types.INTEGER));
        return base.update(sql, params);
    }

    @Override
    public int delete(Borrow borrow) {
        String sql = "delete from borrow where reader_id = ? and book_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(borrow.getReader_id(), Types.INTEGER));
        params.add(new SqlParam(borrow.getBook_id(), Types.INTEGER));
        return base.update(sql, params);
    }

    @Override
    public Borrow check(Borrow borrow) {
        String sql = "select * from borrow where reader_id = ? and book_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(borrow.getReader_id(), Types.INTEGER));
        params.add(new SqlParam(borrow.getBook_id(), Types.INTEGER));
        return base.query(sql, params, new BeanHandler<>(Borrow.class));
    }

    @Override
    public List<Book> selectBookByReaderId(Long readerId) {
        String sql = "select c1.id, c1.title, c1.isbn, c1.author, c1.num, c1.genre_id from books as c1, borrow as c2 where c1.id = c2.book_id and c2.reader_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(readerId, Types.INTEGER));
        return base.query(sql, params, new ListHandler<>(Book.class));
    }
}
