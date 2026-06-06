package data;

public class Genre {
    private Long id;
    private String genre;

    public void  setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }

}
