import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Borrower extends User {
    private String borrowerId;
    private String role;
    private Vector<BookFineTransaction> paidFine;
    private Vector<BorrowingRecord> borrowerBorrowRecord;

    public Borrower(String username, String password, String email, String borrowerId, String role) {
        super(username, password, email);
        this.borrowerId = borrowerId;
        this.role = role;
        this.paidFine = new Vector<BookFineTransaction>();
        this.borrowerBorrowRecord = new Vector<BorrowingRecord>();
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addTransaction(BookFineTransaction transaction) {
        paidFine.addElement(transaction);
    }

    public void addBorrowingRecord(BorrowingRecord record) {
        borrowerBorrowRecord.addElement(record);
    }
    
    public void addReturningRecord(String Id, String date) {
        for(BorrowingRecord r:borrowerBorrowRecord){
            if(r.getRecordId().equals(Id)){
                r.setReturnDate(date);
            }
        }
    }

    public String displayBorrowerInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Username     : ").append(super.getUserName()).append("\n");
        info.append("Email              : ").append(super.getEmail()).append("\n");
        info.append("Borrower ID  : ").append(borrowerId).append("\n");
        info.append(String.format("Fine Amount  : RM %.2f%n", calculateFine()));
        return info.toString();
    }

    public double calculateFine() {
        double fineAmount = 0.00;
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (BorrowingRecord record : borrowerBorrowRecord) {
            LocalDate borrowDate = LocalDate.parse(record.getBorrowDate(), formatter);
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(borrowDate, today);
            if (daysBetween > 14 && record.getReturnDate().isEmpty()) {
                double finePerDay = 2.5;
                double fine = (daysBetween - 14) * finePerDay;
                fineAmount += fine;
            }
        }
        return fineAmount;
    }

    public Vector<BorrowingRecord> getBorrowingRecord(){
        return borrowerBorrowRecord;
    }

    public Vector<BookFineTransaction> getFineTransaction(){
        return paidFine;
    }

    public boolean hasRecordId(String id) {
        for (BorrowingRecord record : borrowerBorrowRecord) {
            if (record.getRecordId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
