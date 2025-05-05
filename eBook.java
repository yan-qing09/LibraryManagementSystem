public class eBook extends Book {
  private String link;

  public eBook(String bookId, String title, String author, String genre, String link) {
    super(bookId, title, author, genre, true);
    this.link = link;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
