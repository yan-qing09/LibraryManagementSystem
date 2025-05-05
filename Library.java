import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

public class Library {
    public static int librarianMenu() {
        Object[] options = {
                "Add User",
                "Add Book",
                "Add eBook",
                "Record Borrowing",
                "Record Returning",
                "Exit"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Select an option:",
                "Library Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.CLOSED_OPTION) {
            return -1;
        }

        return choice;
    }

    public static int borrowerMenu() {
        Object[] options = {
                "Search Book",
                "View Borrowed Books",
                "View Fine Transaction",
                "View Borrower Info",
                "Exit"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Select an option:",
                "Library Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.CLOSED_OPTION) {
            return -1;
        }

        return choice;
    }

    public static void main(String[] args) {
        Vector<User> userList = Data.readUsersFromFile("Librarian.txt", "Borrower.txt");
        Vector<Borrower> borrowerList = Data.filterBorrowers(userList);
        Vector<Book> allBookList = Data.readBooksFromFile("Books.txt", "eBooks.txt");
        Vector<BorrowingRecord> borrowingRecordList = new Vector<BorrowingRecord>();
        Vector<BookFineTransaction> fineTransactionList = new Vector<BookFineTransaction>();
        DecimalFormat df = new DecimalFormat("0.00");
        boolean loggedIn = false;
        boolean resume = true;

        while (!loggedIn) {
            JPanel panel = new JPanel();
            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(10);
            JLabel emptyLabel = new JLabel("\n");
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(10);

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(usernameLabel);
            panel.add(usernameField);
            panel.add(emptyLabel);
            panel.add(passwordLabel);
            panel.add(passwordField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User logged = User.login(userList, username, password);
                if (logged != null) {
                    JOptionPane.showMessageDialog(null, "        Login successful !\n      Welcome, " + logged.getUserName());
                    loggedIn = true;

                    if (logged instanceof Librarian) {
                        resume = true;
                        while (resume) {
                            int choice = librarianMenu();
                            if (choice == -1) {
                                resume = false;
                                continue;
                            }

                            switch (choice) {
                                case 0:
                                    JPanel addUserPanel = new JPanel();
                                    addUserPanel.setLayout(new GridLayout(5, 2));

                                    JLabel addUsernameLabel = new JLabel("Username:");
                                    JTextField addUsernameField = new JTextField(20);
                                    JLabel addPasswordLabel = new JLabel("Password:");
                                    JTextField addPasswordField = new JTextField(20);
                                    JLabel addEmailLabel = new JLabel("Email:");
                                    JTextField addEmailField = new JTextField(20);
                                    JLabel addBorrowerIdLabel = new JLabel("Borrower ID:");
                                    JTextField addBorrowerIdField = new JTextField(20);
                                    JLabel addRoleLabel = new JLabel("Role:");
                                    JTextField addRoleField = new JTextField(20);

                                    addUserPanel.add(addUsernameLabel);
                                    addUserPanel.add(addUsernameField);
                                    addUserPanel.add(addPasswordLabel);
                                    addUserPanel.add(addPasswordField);
                                    addUserPanel.add(addEmailLabel);
                                    addUserPanel.add(addEmailField);
                                    addUserPanel.add(addBorrowerIdLabel);
                                    addUserPanel.add(addBorrowerIdField);
                                    addUserPanel.add(addRoleLabel);
                                    addUserPanel.add(addRoleField);

                                    int userResult = JOptionPane.showConfirmDialog(null, addUserPanel, "Add User", JOptionPane.OK_CANCEL_OPTION);

                                    if (userResult == JOptionPane.OK_OPTION) {
                                        String newUsername = addUsernameField.getText();
                                        String newPassword = addPasswordField.getText();
                                        String newEmail = addEmailField.getText();
                                        String newBorrowerID = addBorrowerIdField.getText();
                                        String newRole = addRoleField.getText();

                                        userList.add(new Borrower(newUsername, newPassword, newEmail, newBorrowerID,
                                                newRole));
                                        JOptionPane.showMessageDialog(null, "User Added Successfully!");
                                    }
                                    break;

                                case 1:
                                    JPanel addBookPanel = new JPanel();
                                    addBookPanel.setLayout(new GridLayout(5, 2));
                                    
                                    JLabel addBookIdLabel = new JLabel("Book ID:");
                                    JTextField addBookIdField = new JTextField(20);
                                    JLabel addTitleLabel = new JLabel("Title:");
                                    JTextField addTitleField = new JTextField(20);
                                    JLabel addAuthorLabel = new JLabel("Author:");
                                    JTextField addAuthorField = new JTextField(20);
                                    JLabel addGenreLabel = new JLabel("Genre:");
                                    JTextField addGenreField = new JTextField(20);
                                    JLabel addAvailabilityLabel = new JLabel("Availability:");
                                    String[] availabilityOptions = {"Available", "Not Available"};
                                    JComboBox<String> addAvailabilityComboBox = new JComboBox<>(availabilityOptions);
                                    
                                    addBookPanel.add(addBookIdLabel);
                                    addBookPanel.add(addBookIdField);
                                    addBookPanel.add(addTitleLabel);
                                    addBookPanel.add(addTitleField);
                                    addBookPanel.add(addAuthorLabel);
                                    addBookPanel.add(addAuthorField);
                                    addBookPanel.add(addGenreLabel);
                                    addBookPanel.add(addGenreField);
                                    addBookPanel.add(addAvailabilityLabel);
                                    addBookPanel.add(addAvailabilityComboBox);
                                    
                                    int bookResult = JOptionPane.showConfirmDialog(null, addBookPanel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
                                    
                                    if (bookResult == JOptionPane.OK_OPTION) {
                                        String newBookId = addBookIdField.getText();
                                        String newTitle = addTitleField.getText();
                                        String newAuthor = addAuthorField.getText();
                                        String newGenre = addGenreField.getText();
                                        boolean newAvailability = addAvailabilityComboBox.getSelectedItem().equals("Available");
                                    
                                        allBookList.add(new Book(newBookId, newTitle, newAuthor, newGenre, newAvailability));
                                        JOptionPane.showMessageDialog(null, "Book Added Successfully!");
                                    }
                                    break;
                                
                                case 2:
                                    JPanel addEBookPanel = new JPanel();
                                    addEBookPanel.setLayout(new GridLayout(5, 2));

                                    JLabel addEBookIdLabel = new JLabel("eBook ID:");
                                    JTextField addEBookIdField = new JTextField(20);
                                    JLabel addETitleLabel = new JLabel("Title:");
                                    JTextField addETitleField = new JTextField(20);
                                    JLabel addEAuthorLabel = new JLabel("Author:");
                                    JTextField addEAuthorField = new JTextField(20);
                                    JLabel addEGenreLabel = new JLabel("Genre:");
                                    JTextField addEGenreField = new JTextField(20);
                                    JLabel addELinkLabel = new JLabel("Link:");
                                    JTextField addELinkField = new JTextField(20);

                                    addEBookPanel.add(addEBookIdLabel);
                                    addEBookPanel.add(addEBookIdField);
                                    addEBookPanel.add(addETitleLabel);
                                    addEBookPanel.add(addETitleField);
                                    addEBookPanel.add(addEAuthorLabel);
                                    addEBookPanel.add(addEAuthorField);
                                    addEBookPanel.add(addEGenreLabel);
                                    addEBookPanel.add(addEGenreField);
                                    addEBookPanel.add(addELinkLabel);
                                    addEBookPanel.add(addELinkField);

                                    int eBookResult = JOptionPane.showConfirmDialog(null, addEBookPanel, "Add eBook", JOptionPane.OK_CANCEL_OPTION);

                                    if (eBookResult == JOptionPane.OK_OPTION) {
                                        String newEBookId = addEBookIdField.getText();
                                        String newETitle = addETitleField.getText();
                                        String newEAuthor = addEAuthorField.getText();
                                        String newEGenre = addEGenreField.getText();
                                        String newELink = addELinkField.getText();

                                        allBookList.add(new eBook(newEBookId, newETitle, newEAuthor, newEGenre, newELink));
                                        JOptionPane.showMessageDialog(null, "eBook Added Successfully!");
                                    }
                                    break;

                                case 3:
                                    JPanel recordBorrowingPanel = new JPanel();
                                    recordBorrowingPanel.setLayout(new GridLayout(3, 2));

                                    JLabel borrowingRecordIdLabel = new JLabel("Borrowing Record ID:");
                                    JTextField borrowingRecordIdField = new JTextField(20);
                                    JLabel borrowedBookIdLabel = new JLabel("Book ID:");
                                    JTextField borrowedBookIdField = new JTextField(20);
                                    JLabel borrowerIdLabel = new JLabel("Borrower ID:");
                                    JTextField borrowerIdField = new JTextField(20);

                                    recordBorrowingPanel.add(borrowingRecordIdLabel);
                                    recordBorrowingPanel.add(borrowingRecordIdField);
                                    recordBorrowingPanel.add(borrowedBookIdLabel);
                                    recordBorrowingPanel.add(borrowedBookIdField);
                                    recordBorrowingPanel.add(borrowerIdLabel);
                                    recordBorrowingPanel.add(borrowerIdField);

                                    int borrowingResult = JOptionPane.showConfirmDialog(null, recordBorrowingPanel, "Record Borrowing", JOptionPane.OK_CANCEL_OPTION);

                                    if (borrowingResult == JOptionPane.OK_OPTION) {
                                        String newBorrowingRecordId = borrowingRecordIdField.getText();
                                        String borrowedBookId = borrowedBookIdField.getText();
                                        String borrowerId = borrowerIdField.getText();
                                        // LocalDate borrowingDate = LocalDate.now().minusDays(15);
                                        LocalDate borrowingDate = LocalDate.now();
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        String formattedBorrowingDate = borrowingDate.format(format);

                                        Book borrowedBook = null;
                                        for (Book book : allBookList) {
                                            if (book.getBookId().equals(borrowedBookId)) {
                                                borrowedBook = book;
                                                break;
                                            }
                                        }

                                        Borrower borrower = null;
                                        for (User user : userList) {
                                            if (user instanceof Borrower) {
                                                Borrower tempBorrower = (Borrower) user;
                                                if (tempBorrower.getBorrowerId().equals(borrowerId)) {
                                                    borrower = tempBorrower;
                                                    break;
                                                }
                                            }
                                        }

                                        if (borrowedBook != null && borrower != null) {
                                            if (borrowedBook.isAvailability()) {
                                                BorrowingRecord temp = new BorrowingRecord(newBorrowingRecordId, formattedBorrowingDate, borrowedBook);
                                                borrowingRecordList.add(new BorrowingRecord(newBorrowingRecordId, formattedBorrowingDate, borrowedBook));
                                                borrowedBook.setAvailability(false); // Set availability to false when borrowed
                                                borrower.addBorrowingRecord(temp);
                                                JOptionPane.showMessageDialog(null, "Borrowing Recorded Successfully! The borrower should return the book in 14 days.");
                                                } 
                                            
                                            else {
                                                JOptionPane.showMessageDialog(null, "Book is currently unavailable.");
                                            }
                                        } 
                                        
                                        else {
                                            if (borrowedBook == null) {
                                                JOptionPane.showMessageDialog(null, "Book ID not found.");
                                            }

                                            if (borrower == null) {
                                                JOptionPane.showMessageDialog(null, "Borrower ID not found.");
                                            }
                                        }
                                    }
                                    break;

                                case 4:
                                    String recordId = JOptionPane.showInputDialog("Borrowing Record ID:");
                                    Borrower temp = null;
                                    if (recordId == null) {
                                        break;
                                    }

                                    LocalDate returnDate = LocalDate.now();
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    String formattedReturnDate = returnDate.format(format);

                                    BorrowingRecord returningRecord = null;
                                    for (BorrowingRecord record : borrowingRecordList) {
                                        if (record.getRecordId().equalsIgnoreCase(recordId)) {
                                            returningRecord = record;
                                            break;
                                        }
                                    }
                                    for (Borrower brw : borrowerList) {
                                        if (brw.hasRecordId(recordId)) {
                                            temp = brw;
                                            break;
                                        }
                                    }

                                    if (temp == null) {
                                        JOptionPane.showMessageDialog(null, "Borrower with Record ID " + recordId + " not found.");
                                        break;
                                    }

                                    if (returningRecord != null) {
                                        returningRecord.setReturnDate(formattedReturnDate);
                                        temp.addReturningRecord(recordId,formattedReturnDate);
                                        JOptionPane.showMessageDialog(null, "Book returned successfully.");

                                        returningRecord.getBook().setAvailability(true);

                                        LocalDate borrowingDate = LocalDate.parse(returningRecord.getBorrowDate(), format);
                                        LocalDate dueDate = borrowingDate.plusDays(14);

                                        if (returnDate.isAfter(dueDate)) {
                                            int confirm = JOptionPane.showConfirmDialog(null,"The return is late. Do you want to add a fine transaction record?");
                                            if (confirm == JOptionPane.YES_OPTION) {
                                                double fineAmount = returningRecord.calculateFine(returnDate, dueDate);

                                                JPanel finePanel = new JPanel();
                                                finePanel.setLayout(new GridLayout(3, 2));

                                                JLabel transactionIdLabel = new JLabel("Transaction ID:");
                                                JTextField transactionIdField = new JTextField(10);
                                                JLabel fineAmountLabel = new JLabel("Fine Amount:");
                                                JTextField fineAmountField = new JTextField(10);
                                                fineAmountField.setText(df.format(fineAmount)); 
                                                fineAmountField.setEditable(false);
                                                JLabel payDateLabel = new JLabel("Pay Date:");
                                                JTextField payDateField = new JTextField(10);
                                                payDateField.setText(formattedReturnDate);

                                                finePanel.add(transactionIdLabel);
                                                finePanel.add(transactionIdField);
                                                finePanel.add(fineAmountLabel);
                                                finePanel.add(fineAmountField);
                                                finePanel.add(payDateLabel);
                                                finePanel.add(payDateField);

                                                int fineResult = JOptionPane.showConfirmDialog(null, finePanel, "Add Fine Transaction", JOptionPane.OK_CANCEL_OPTION);

                                                if (fineResult == JOptionPane.OK_OPTION) {
                                                    String transactionId = transactionIdField.getText();
                                                    BookFineTransaction fineTransaction = new BookFineTransaction(transactionId, fineAmount, formattedReturnDate);
                                                    fineTransactionList.add(fineTransaction);
                                
                                                    temp.addTransaction(fineTransaction);
                                                    JOptionPane.showMessageDialog(null, "Fine Recorded Successfully.");
                                                } 
                                                
                                                else {
                                                    JOptionPane.showMessageDialog(null, "Fine transaction record not added.");
                                                }
                                            } 
                                            
                                            else {
                                                JOptionPane.showMessageDialog(null, "The return is not late.");
                                            }
                                        }
                                    } 
                                    
                                    else {
                                        JOptionPane.showMessageDialog(null, "Borrowing Record with ID " + recordId + " not found.");
                                    }
                                    break;

                                case 5:
                                    JOptionPane.showMessageDialog(null, "Logging out...");
                                    resume = false;
                                    loggedIn = false;
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                                    break;
                            }
                        }
                    } 
                    
                    else if (logged instanceof Borrower) {
                        resume = true;
                        while (resume) {
                            int choice = borrowerMenu();
                            if (choice == -1) {
                                resume = false;
                                continue;
                            }
                            Borrower loggedBorrower = (Borrower) logged;

                            switch (choice) {
                                case 0:
                                    JPanel panel2 = new JPanel();
                                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
                                    panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                    JPanel searchWayPanel = new JPanel();
                                    searchWayPanel.setLayout(new BoxLayout(searchWayPanel, BoxLayout.X_AXIS));
                                    JLabel searchWayLabel = new JLabel("Search by Title or Book ID: ");
                                    String[] searchOptions = { "Title", "Book ID" };
                                    JComboBox<String> searchWayList = new JComboBox<>(searchOptions);
                                    searchWayPanel.add(searchWayLabel);
                                    searchWayPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                                    searchWayPanel.add(searchWayList);
                                    JPanel keywordPanel = new JPanel();
                                    keywordPanel.setLayout(new BoxLayout(keywordPanel, BoxLayout.X_AXIS));
                                    JLabel keywordLabel = new JLabel("Enter Keyword: ");
                                    JTextField keywordField = new JTextField(10);
                                    keywordPanel.add(keywordLabel);
                                    keywordPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                                    keywordPanel.add(keywordField);

                                    panel2.add(searchWayPanel);
                                    panel2.add(Box.createRigidArea(new Dimension(0, 10)));
                                    panel2.add(keywordPanel);

                                    int result2 = JOptionPane.showConfirmDialog(null, panel2, "Search Book", JOptionPane.OK_CANCEL_OPTION);
                                    if (result2 == JOptionPane.OK_OPTION) {
                                        int searchWay = searchWayList.getSelectedIndex() + 1;
                                        String keyword = keywordField.getText();
                                        BookSearching search = new BookSearching(searchWay, keyword, allBookList);
                                        Vector<Book> searchResult = search.getSearchResults();
                                        if (!searchResult.isEmpty()) {
                                            Vector<Object[]> dataBook = new Vector<>();
                                            Vector<Object[]> dataEBook = new Vector<>();

                                            for (Book book : searchResult) {
                                                if (book instanceof eBook) {
                                                    eBook eBook = (eBook) book;
                                                    dataEBook.add(new Object[] { eBook.getBookId(), eBook.getTitle(),
                                                            eBook.getAuthor(), eBook.getGenre(), eBook.getLink() });
                                                } 
                                                
                                                else {
                                                    dataBook.add(new Object[] { book.getBookId(), book.getTitle(),
                                                            book.getAuthor(), book.getGenre(),book.getAvailability() });
                                                }
                                            }

                                            String[] columnNamesBook = new String[] { "Book ID", "Title", "Author", "Genre","Availability" };
                                            String[] columnNamesEBook = new String[] { "Book ID", "Title", "Author", "Genre", "Link" };
 
                                            if (!dataBook.isEmpty()) {
                                                DefaultTableModel bookTableModel = new DefaultTableModel(dataBook.toArray(new Object[dataBook.size()][]), columnNamesBook);
                                                JTable tableBook = new JTable(bookTableModel);
                                                JScrollPane scrollPaneBook = new JScrollPane(tableBook);
                                                JOptionPane.showMessageDialog(null, scrollPaneBook,"Search Results (Books)", JOptionPane.PLAIN_MESSAGE);
                                            }
                                            if (!dataEBook.isEmpty()) {
                                                DefaultTableModel eBookTableModel = new DefaultTableModel(dataEBook.toArray(new Object[dataEBook.size()][]), columnNamesEBook);
                                                JTable tableEBook = new JTable(eBookTableModel);
                                                JScrollPane scrollPaneEBook = new JScrollPane(tableEBook);
                                                JOptionPane.showMessageDialog(null, scrollPaneEBook,"Search Results (eBooks)", JOptionPane.PLAIN_MESSAGE);
                                            }
                                        } 
                                        
                                        else {
                                            JOptionPane.showMessageDialog(null, "No books found matching the search criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    }
                                    break;

                                case 1:
                                    Vector<BorrowingRecord> borrowerRecord = loggedBorrower.getBorrowingRecord();
                                    if (borrowerRecord.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "No Record Found.", "Borrowed Books", JOptionPane.INFORMATION_MESSAGE);
                                    } 
                                    
                                    else {
                                        Object[][] data = new Object[borrowerRecord.size()][4];
                                        String[] columnNames = { "Record ID", "Borrow Date", "Return Date","Book Title" };
                                        for (int i = 0; i < borrowerRecord.size(); i++) {
                                            BorrowingRecord brecord = borrowingRecordList.get(i);
                                            data[i][0] = brecord.getRecordId();
                                            data[i][1] = brecord.getBorrowDate();
                                            data[i][2] = brecord.getReturnDate();
                                            data[i][3] = brecord.getBookTitle();
                                        }
                                        JTable BRtable = new JTable(data, columnNames);
                                        BRtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                                        JScrollPane scrollPane = new JScrollPane(BRtable);
                                        scrollPane.setPreferredSize(new Dimension(500, 200));

                                        JOptionPane.showMessageDialog(null, scrollPane, "Borrowed Books", JOptionPane.PLAIN_MESSAGE);
                                    }
                                    break;

                                case 2:
                                    Vector<BookFineTransaction> borrowerTransaction = loggedBorrower.getFineTransaction();
                                    if (borrowerTransaction.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "No Record Found.", "Fine Transactions", JOptionPane.INFORMATION_MESSAGE);
                                    }

                                    else {
                                        Object[][] fineData = new Object[borrowerTransaction.size()][3];
                                        String[] fineColumnNames = { "Transaction ID", "Payment Date", "Fine Amount (RM)" };

                                        for (int i = 0; i < borrowerTransaction.size(); i++) {
                                            BookFineTransaction transaction = borrowerTransaction.get(i);
                                            fineData[i][0] = transaction.getTransactionID();
                                            fineData[i][1] = transaction.getPayDate();
                                            fineData[i][2] = df.format(transaction.getAmount());
                                        }
                                        JTable fineTable = new JTable(fineData, fineColumnNames);
                                        fineTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                                        JScrollPane fineScrollPane = new JScrollPane(fineTable);
                                        fineScrollPane.setPreferredSize(new Dimension(500, 200));

                                        JOptionPane.showMessageDialog(null, fineScrollPane, "Fine Transactions", JOptionPane.PLAIN_MESSAGE);
                                    }
                                    break;

                                case 3:
                                    JOptionPane optionPane = new JOptionPane(loggedBorrower.displayBorrowerInfo(), JOptionPane.INFORMATION_MESSAGE);
                                    optionPane.setPreferredSize(new Dimension(300, 180));
                                    JDialog dialog = optionPane.createDialog("Borrower Info");
                                    dialog.setVisible(true);
                                    break;

                                case 4:
                                    JOptionPane.showMessageDialog(null, "Logging out...");
                                    resume = false;
                                    loggedIn = false;
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                                    break;
                            }
                        }
                    }
                } 
                
                else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            } 
            
            else {
                System.exit(0);
            }
        }
    }
}