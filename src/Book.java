import java.util.ArrayList;

public class Book {

    private String Title;
    private String Author;
    private String Category;
    private double Price;
    private String Status;

    public Book() {

    }
    public Book(String title, String author, String category, double price,String status) {
        Title = title;
        Author = author;
        Category = category;
        Price = price;
        Status= status;
    }

    public static ArrayList<Book> showBooks(String Title,String Author,String Category,double Price) {

       return DatabaseConnection.showCategorizedBooks(Title);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



}