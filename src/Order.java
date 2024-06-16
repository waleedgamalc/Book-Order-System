public class Order {


    private int order_id;
    private String user;
    private String bookname;
    private double cost;
    private String status;

    public Order() {

    }
    public Order(int order_id,String user, String bookname, double cost, String status) {
        this.order_id = order_id;
        this.user = user;
        this.bookname = bookname;
        this.cost = cost;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

}
