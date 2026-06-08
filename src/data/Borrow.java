package data;

public class Borrow {
    private Long id;
    private Long reader_id;
    private Long book_id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getReader_id() {
        return reader_id;
    }
    public void setReader_id(Long reader_id) {
        this.reader_id = reader_id;
    }
    public Long getBook_id() {
        return book_id;
    }
    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

}
