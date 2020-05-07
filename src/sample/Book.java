package sample;

public class Book {

    private Integer id;
    private String title;
    private String author;
    private String genre;
    private Integer quantity;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book(Integer id, String title, String author, String genre, Integer quantity, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {

        return "Book id: " + id +
                ", title:'" + title + '\'' +
                ", author:'" + author + '\'' +
                ", genre:'" + genre + '\'' +
                ", quantity:" + quantity +
                ", price_" + price;
    }
}
