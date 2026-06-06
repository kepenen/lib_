package service;

import data.Genre;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public interface GenreService {
    List<Genre> select();
    int add(Genre g);
    int delete(Genre g);
    int update(Genre g);
}
