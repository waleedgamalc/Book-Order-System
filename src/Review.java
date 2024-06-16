public class Review {

    private String username;
    private String Book;
    private String Review;

    public Review (){};

    public Review(String username, String book, String review) {
        this.username = username;
        Book = book;
        Review = review;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBook() {
        return Book;
    }

    public void setBook(String book) {
        Book = book;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }
}
