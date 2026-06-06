package data;

public class Book {
    private Long id;
    private String title;
    private String isbn;
    private String author;
    private Long num;
    private Long genre_id;

    public Book(Long id, String title, String isbn, String author, Long num, Long genre_id) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.num = num;
        this.genre_id = genre_id;
    }

    public Book(String title, String isbn, String author, Long num, Long genre_id) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.num = num;
        this.genre_id = genre_id;
    }

    public Book() {

    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public Long getNum() {
        return num;
    }
    public void setNum(Long num) {
        this.num = num;
    }
    public Long getGenre_id() {
        return genre_id;
    }
    public void setGenre_id(Long genre_id) {
        this.genre_id = genre_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
