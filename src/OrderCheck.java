public abstract class OrderCheck {

   private int order_id;

    public OrderCheck() {

    }

    public OrderCheck(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void decision(int id){};
}
