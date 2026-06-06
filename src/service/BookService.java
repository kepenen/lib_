package service;

import data.Book;

import java.util.List;

public interface BookService {
    List<Book> selectByGenreID(Integer genre_id);
    int insert(Book b);
    int deleteBooks(int[] ids);
    int update(Book b);
}
