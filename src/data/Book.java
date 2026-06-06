package data;

public class Book {
    public Integer id;
    public String title;
    public String isbn;
    public String author;
    public Integer num;
    public Integer genre_id;

    public Book(Integer id, String title, String isbn, String author, Integer num, Integer genre_id) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.num = num;
        this.genre_id = genre_id;
    }

    public Book(String title, String isbn, String author, Integer num, Integer genre_id) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.num = num;
        this.genre_id = genre_id;
    }

    public Book() {
        this.title = "";
        this.isbn = "";
        this.author = "";
        this.num = 0;
        this.genre_id = 0;
    }
}
