import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public  class DatabaseConnection {

    private static Connection con = null;

    private static DatabaseConnection instance = null;
    private DatabaseConnection() {}

    public static DatabaseConnection getInstance() {
        if (instance == null) {

            instance = new DatabaseConnection();

        }

        return instance;
    }
    private static String categorized_books = "Select Title,Author,Category,Price,Status \n" +
            "From books\n" +
            "Where Category =?;";
    private static String addRow = "INSERT INTO orders (user, bookname, cost,Status) VALUES (?, ?, ?,?)";
    private static String all_books = "SELECT * \n" + "FROM books;";
    private static String all_orders = "SELECT * \n" + "FROM orders;";
    private static String user_orders = "Select order_id,user,bookname,cost,Status \n" +
            "From orders\n" +
            "Where user =?;";
    private static String book_review = "Select user,book,review \n" +
            "From reviews\n" +
            "Where book =?;";
    private static String add_Review = "INSERT INTO reviews (user, book,review) VALUES (?, ?, ?)";
    private static String cancel_order = "UPDATE orders SET `Status`='Cancelled' WHERE `order_id`= ? ;";
    private static String update_price = "UPDATE books SET `Price`=? WHERE `Title`= ? ;";
    private static String delete_book = "DELETE FROM books WHERE `Title`= ? ;";
    private static String EditInfo = "UPDATE books SET Price = ? , Status = ? WHERE `Title`= ? ;";
    private static String add_Book = "INSERT INTO books (Title, Author,Category,Price,Status) VALUES (?, ?, ?,?,?)";
    private static String all_categories = "SELECT * \n" + "FROM category;";
    private static String add_cat = "INSERT INTO category (Category) VALUES (?)";
    private static String delete_cat = "DELETE FROM category WHERE `Category`= ? ;";
    private static String stat = "SELECT bookname, COUNT(*) AS sales\n" +
            "FROM orders\n" +
            "GROUP BY bookname\n" +
            "ORDER BY sales DESC;";



    static {
        String url = "jdbc:mysql://localhost/bookstore?serverTimezone=UTC";
        String user = "root";
        String pass = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String unicode = "useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
            return DriverManager.getConnection("jdbc:mysql://localhost/bookstore?", "root", "");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("couldn't connect!");
            throw new RuntimeException(ex);
        }
    }

    public static ArrayList<Book> showCategorizedBooks(String category) {
        getConnection();

        ArrayList<Book> book = new ArrayList<Book>();
        try {

            PreparedStatement preparedStatement = con.prepareStatement(categorized_books);
            preparedStatement.setString(1, category);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                book.add(new Book(rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Category"),
                        rs.getDouble("Price"),
                        rs.getString("Status")
                ));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;

    }

    public static ArrayList<Book> showAllBooks() {
        getConnection();

        ArrayList<Book> book = new ArrayList<Book>();
        try {

            PreparedStatement preparedStatement = con.prepareStatement(all_books);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                book.add(new Book(rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Category"),
                        rs.getDouble("Price"),
                        rs.getString("Status")
                ));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;

    }
    public static ArrayList<String> allCategories() {
        getConnection();

        ArrayList<String> categs = new ArrayList<String>();
        try {

            PreparedStatement preparedStatement = con.prepareStatement(all_categories);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                categs.add(rs.getString("Category"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categs;

    }


    public static void addToDatabase(Order order) {

        getConnection();

        try {

             PreparedStatement preparedStatement = con.prepareStatement(addRow);
             preparedStatement.setString(1, order.getUser());
             preparedStatement.setString(2, order.getBookname());
             preparedStatement.setDouble(3, order.getCost());
            preparedStatement.setString(4, order.getStatus());
             int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        "Successfully Added to the Cart!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to Add",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }

    }

    public static ArrayList<Order> retrieve(String user) {

        getConnection();
        ArrayList<Order> order = new ArrayList<Order>();
        try {

            PreparedStatement preparedStatement = con.prepareStatement(user_orders);
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                order.add(new Order(rs.getInt("order_id"),
                        rs.getString("user"),
                        rs.getString("bookname"),
                        rs.getDouble("cost"),
                        rs.getString("Status")
                ));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;

    }
    public static ArrayList<Review> retrieveReviews(String book) {

        getConnection();
        ArrayList<Review> review = new ArrayList<Review>();
        try {

            PreparedStatement preparedStatement = con.prepareStatement(book_review);
            preparedStatement.setString(1, book);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                review.add(new Review(rs.getString("user"),
                        rs.getString("book"),
                        rs.getString("review")
                ));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return review;

    }

    public static void addReviewToReviews(Review review) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(add_Review);
            preparedStatement.setString(1, review.getUsername());
            preparedStatement.setString(2, review.getBook());
            preparedStatement.setString(3, review.getReview());
            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Review Added Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to Add",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void cancelOrder(int id) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(cancel_order);
            preparedStatement.setInt(1, id);
            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Order is Cancelled Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to cancel",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void deleteBook(String b) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(delete_book);
            preparedStatement.setString(1, b);
            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Book is Deleted Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to delete",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void editBook(double price,String status,String book) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(EditInfo);
            preparedStatement.setDouble(1, price);
            preparedStatement.setString(2, status);
            preparedStatement.setString(3, book);
            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Book is Edited Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to Edit",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void addBook(String T,String A,String C,double P , String S) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(add_Book);
            preparedStatement.setString(1, T);
            preparedStatement.setString(2, A);
            preparedStatement.setString(3, C);
            preparedStatement.setDouble(4, P);
            preparedStatement.setString(5, S);

            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Book is Added Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to Add",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void addCat(String category) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(add_cat);
            preparedStatement.setString(1, category);

            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Category is Added Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to Add",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void deleteCat(String category) {

        getConnection();

        try {

            PreparedStatement preparedStatement = con.prepareStatement(delete_cat);
            preparedStatement.setString(1, category);

            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Category is Deleted Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to Delete",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<Order> showallOrders() {

            getConnection();

            ArrayList<Order> order = new ArrayList<Order>();
            try {

                PreparedStatement preparedStatement = con.prepareStatement(all_orders);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    order.add(new Order(rs.getInt("order_id"),
                            rs.getString("user"),
                            rs.getString("bookname"),
                            rs.getDouble("cost"),
                            rs.getString("Status")
                    ));
                }
//             con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return order;
    }
    public static boolean checkExisitingOfBook(String BookName) {

        getConnection();
        boolean exist = false;
        try {

            PreparedStatement preparedStatement = con.prepareStatement(all_books);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                if(rs.getString("Title").equals(BookName)){

                    exist = true;

                    }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exist ;

    }
    public static ArrayList<Statistics> showSales() {
        getConnection();

        ArrayList<Statistics> s = new ArrayList<Statistics>();
        try {

            PreparedStatement preparedStatement = con.prepareStatement(stat);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                s.add(new Statistics(rs.getString("bookname"),
                        rs.getInt("sales")
                ));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return s;

    }
}
