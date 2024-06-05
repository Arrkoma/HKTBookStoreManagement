
package bookstoremanagement;

public class Book {
    private String ISBN;
    private String title;
    private double price;
    private String authorID;

    public Book(String ISBN, String title, double price, String authorID) {
        this.ISBN = ISBN;
        this.title = title;
        this.price = price;
        this.authorID = authorID;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }
}
