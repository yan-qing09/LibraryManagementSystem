import java.util.*;

public class Book {
    protected String bookId;
    protected String title;
    protected String author;
    protected String genre;
    protected boolean availability;
    protected Vector<BorrowingRecord> bookBorrowingRecord;


    public Book(String bookId, String title, String author, String genre, boolean availability) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
        this.bookBorrowingRecord = new Vector<>();
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getAvailability() {
        return availability ? "Available" : "Borrowed";
    }
    
    public void addBorrowingRecord(BorrowingRecord borrowingRecord) {
        bookBorrowingRecord.addElement(borrowingRecord);
    }
}
