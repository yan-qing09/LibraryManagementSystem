import java.time.LocalDate;

public class BorrowingRecord {
    private String recordId;
    private String borrowDate;
    private String returnDate;
    private Book book;

    public BorrowingRecord(String recordId, String borrowDate, Book book) {
        this.recordId = recordId;
        this.borrowDate = borrowDate;
        this.returnDate = "";
        this.book = book;
        this.book.setAvailability(false);
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public String getBookTitle() {
        return book.getTitle();
    }

    public double calculateFine(LocalDate returnDate, LocalDate dueDate) {
        double fineAmount = 0.00;

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
        if (daysBetween > 0) {
            double finePerDay = 2.5; 
            fineAmount = daysBetween * finePerDay;
        }

        return fineAmount;
    }

    public void returnBook(String returnDate) {
        this.returnDate = returnDate;
        this.book.setAvailability(true);
    }
}
