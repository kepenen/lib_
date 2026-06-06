package service.impl;

import data.Book;
import service.BookService;
import service.sql.MySQLConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookImpl extends MySQLConnection implements BookService {

    @Override
    public List<Book> selectByGenreID(Integer genre_id) {
        // SELECT id, title, isbn, author, num, genre_id FROM books WHERE genre_id =
        String sql = "select * from books where genre_id = '" + genre_id + "'";
        List<Book> books = new ArrayList<>();
        try {
            rs = stmt.executeQuery(sql);
            if (rs == null) {
                return books;
            }
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("isbn"),
                        rs.getString("author"),
                        rs.getInt("num"),
                        rs.getInt("genre_id")));
            }
        } catch (SQLException e) {
            showDialog(this.toString(), e);
        }
        return books;
    }

    @Override
    public int insert(Book b) {
        String sql = "INSERT INTO books(title, isbn, author, num, genre_id) VALUES" + "('" +
                b.title + "','" +
                b.isbn + "','" +
                b.author + "','" +
                b.num + "','" +
                b.genre_id + "')";
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return 0;
        }
    }

    @Override
    public int deleteBooks(int[] ids) {
        StringBuilder sql = new StringBuilder("delete from books where id in (");
        for (int id : ids) {
            sql.append("'").append(id).append("',");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        try {
            return stmt.executeUpdate(sql.toString());
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return 0;
        }
    }

    @Override
    public int update(Book b) {
        try {
            String sql = "UPDATE books SET " +
                    "title='" + b.title + "'," +
                    "isbn='" + b.isbn + "'," +
                    "author='" + b.author + "'," +
                    "num='" + b.num + "'," +
                    "genre_id='" + b.genre_id + "' " +
                    "where id='" + b.id + "'";
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return 0;
        }
    }
}
