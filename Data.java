import java.io.*;
import java.util.*;

class Data {
    public static Vector<User> readUsersFromFile(String librarianFilename, String borrowerFilename) {
        Vector<User> users = new Vector<User>();

        try {
            Scanner input = new Scanner(new File(librarianFilename));
            while (input.hasNextLine()) {
                String username = input.next();
                String password = input.next();
                String email = input.next();
                String id = input.next();

                Librarian librarian = new Librarian(username, password, email, id);
                users.add(librarian);
            }
            input.close();
        } 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Scanner input = new Scanner(new File(borrowerFilename));
            while (input.hasNextLine()) {
                String username = input.next();
                String password = input.next();
                String email = input.next();
                String role = input.next();
                String studentId = input.next();

                Borrower borrower = new Borrower(username, password, email, role, studentId);
                users.add(borrower); 
            }
            input.close();
        } 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public static Vector<Book> readBooksFromFile(String booksFilename, String eBooksFilename) {
        Vector<Book> books = new Vector<Book>();

        try {
            Scanner input = new Scanner(new File(booksFilename));
            while (input.hasNextLine()) {
                String[] bookInfo = input.nextLine().split(",\\s*", 5);
                String bookId = bookInfo[0];
                String title = bookInfo[1];
                String author = bookInfo[2];
                String genre = bookInfo[3];
                boolean availability = Boolean.parseBoolean(bookInfo[4]);

                Book book = new Book(bookId, title, author, genre, availability);
                books.add(book);
            }
            input.close();
        } 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Scanner input = new Scanner(new File(eBooksFilename));
            while (input.hasNextLine()) {
                String[] eBookInfo = input.nextLine().split(",\\s*", 5);
                String bookId = eBookInfo[0];
                String title = eBookInfo[1];
                String author = eBookInfo[2];
                String genre = eBookInfo[3];
                String link = eBookInfo[4];

                eBook eBook = new eBook(bookId, title, author, genre, link);
                books.add(eBook);
            }
            input.close();
        } 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static Vector<Borrower> filterBorrowers(Vector<User> userList) {
        Vector<Borrower> borrowers = new Vector<>();
        for (User user : userList) {
            if (user instanceof Borrower) {
                borrowers.add((Borrower) user);
            }
        }
        return borrowers;
    }
    
}