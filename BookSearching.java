import java.util.Vector;

public class BookSearching {
    private int choice;
    private String searchKey;
    private Vector<Book> bookList;
    private Vector<Book> searchResults;

    public BookSearching(int choice, String searchKey, Vector<Book> bookList) {
        this.choice = choice;
        this.searchKey = searchKey;
        this.bookList = bookList;
        this.searchResults = new Vector<>();

        if (choice == 1) {
            this.searchResults = searchByTitle(searchKey);
        } else if (choice == 2) {
            this.searchResults = searchByID(searchKey);
        }
    }

    public Vector<Book> searchByTitle(String title) {
        Vector<Book> result = new Vector<>();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public Vector<Book> searchByID(String id) {
        Vector<Book> result = new Vector<>();
        for (Book book : bookList) {
            if (book.getBookId().toLowerCase().equals(id.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public Vector<Book> getSearchResults() {
        return searchResults;
    }
}
