package data;

public class Genre {
    public Integer id;
    public String genre;

    public Genre(Integer id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Genre() {
        id = 0;
        genre = "NULL";
    }
}
