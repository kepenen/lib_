package service;

import data.Book;
import data.Borrow;
import data.Reader;
import dbframe.core.BaseQuery;
import dbframe.core.SqlParam;
import dbframe.handler.BeanHandler;
import dbframe.handler.ListHandler;
import dbframe.handler.ScalarHandler;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BorrowService {
    private final BaseQuery base;
    List<SqlParam> params;

    public BorrowService() {
        base = new BaseQuery();
    }

    public int insert(Borrow borrow) {
        String sql = "insert into borrow(reader_id, book_id) values (?,?)";
        params = new ArrayList<>();
        params.add(new SqlParam(borrow.getReader_id(), Types.INTEGER));
        params.add(new SqlParam(borrow.getBook_id(), Types.INTEGER));
        return base.update(sql, params);
    }

    public int delete(Borrow borrow) {
        String sql = "delete from borrow where reader_id = ? and book_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(borrow.getReader_id(), Types.INTEGER));
        params.add(new SqlParam(borrow.getBook_id(), Types.INTEGER));
        return base.update(sql, params);
    }

    public Borrow check(Borrow borrow) {
        String sql = "select * from borrow where reader_id = ? and book_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(borrow.getReader_id(), Types.INTEGER));
        params.add(new SqlParam(borrow.getBook_id(), Types.INTEGER));
        return base.query(sql, params, new BeanHandler<>(Borrow.class));
    }

    public List<Book> selectBookByReaderId(Long readerId) {
        String sql = "select c1.id, c1.title, c1.isbn, c1.author, c1.num, c1.genre_id from books as c1, borrow as c2 where c1.id = c2.book_id and c2.reader_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(readerId, Types.INTEGER));
        return base.query(sql, params, new ListHandler<>(Book.class));
    }

    public Long getCountByBookId(Long bookId) {
        String sql = "select count(*) from borrow where book_id = ?";
        params = new ArrayList<>();
        params.add(new SqlParam(bookId, Types.INTEGER));
        return base.query(sql, params, new ScalarHandler<Long>());
    }

    public List<Reader> selectBorrowers() {
        String sql = "select * from readers where id in (select reader_id from borrow group by reader_id)";
        params = new ArrayList<>();
        return base.query(sql, params, new ListHandler<>(Reader.class));
    }
}
