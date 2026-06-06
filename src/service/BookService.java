package service;

import data.Book;

import java.util.List;

public interface BookService {
    List<Book> selectByGenreID(Long genre_id);
    int insert(Book b);
    int deleteBooks(Long[] ids);
    int update(Book b);
}
