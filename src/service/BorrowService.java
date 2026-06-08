package service;

import data.Book;
import data.Borrow;
import data.Reader;

import java.util.List;

public interface BorrowService {
    int insert(Borrow borrow);
    int delete(Borrow borrow);
    Borrow check(Borrow borrow);
    List<Book> selectBookByReaderId(Long readerId);
    Long getCountByBookId(Long bookId);
}
