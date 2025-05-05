public class Librarian extends User {
    private String librarianId;

    public Librarian(String username, String password, String email, String librarianId) {
        super(username, password, email);
        this.librarianId = librarianId;
    }

    public String getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(String librarianId) {
        this.librarianId = librarianId;
    }
}
