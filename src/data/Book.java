package data;

public class Book {
    private Long id;
    private String title;
    private String isbn;
    private String author;
    private Long num;
    private Long genre_id;
    private Long count_borrowed;

    public Book() {

    }

    public void setCount_borrowed(Long count_borrowed) {
        this.count_borrowed = count_borrowed;
    }
    public Long getCount_borrowed() {
        return count_borrowed;
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
