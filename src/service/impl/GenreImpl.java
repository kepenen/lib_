package service.impl;

import data.Genre;
import service.GenreService;
import service.sql.MySQLConnection;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;

public class GenreImpl extends MySQLConnection implements GenreService {
    @Override
    public List<Genre> select() {
        List<Genre> genres = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM genres");
            while (rs.next()) {
                genres.add(new Genre(rs.getInt("id"), rs.getString("genre")));
            }
            return genres;
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return genres;
        }
    }

    public int add(Genre g) {
        String sql = "insert into genres(genre) values" + "('" + g.genre +"')";
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return 0;
        }
    }

    public int delete(Genre g) {
        String sql = "delete from genres where id = " + "'" + g.id + "'";
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return 0;
        }
    }
    public int update(Genre g) {
        String sql = "UPDATE genres SET genre='" + g.genre + "' WHERE id=" + g.id;
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            showDialog(this.toString(), e);
            return 0;
        }
    }
}
