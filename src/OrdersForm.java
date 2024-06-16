import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdersForm extends JDialog {
    private JTable OrdersTable;
    private JButton addReviewButton;
    private JButton cancelOrderButton;
    private JPanel ordersPanel;
    private JTextField tfReview;
    private JScrollPane scrollpanel;
    private JButton btnPlaceOrder;

    public OrdersForm(JFrame parent,String username) {

        super(parent);
        setTitle("Orders");
        setContentPane(ordersPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        showOrders(username);



        addReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int selectedRow = OrdersTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select a Book to add review.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String stat = (String) OrdersTable.getValueAt(selectedRow, 3);
                if (!("Order Confirmed".equals(stat))) {
                    JOptionPane.showMessageDialog(null
                            , "Sorry, you have to purchase the book before add a Review!", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                        return;


                }
                else {
                    String userReview = tfReview.getText();
                    if (userReview.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a review",
                                "Try Again", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    r.setUsername(username);
                    r.setBook((String) OrdersTable.getValueAt(selectedRow, 1));
                    r.setReview(tfReview.getText());
                    DatabaseConnection.addReviewToReviews(r);

                }

            }

        });
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = OrdersTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select an Order to cancel.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }

              int id =  (int) OrdersTable.getValueAt(selectedRow, 0);
                DatabaseConnection.cancelOrder(id);
                showOrders(username);



            }
        });
        btnPlaceOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    BuyForm b = new BuyForm(parent,username);
            }
        });setVisible(true);
    }

    Review r = new Review();

    private void showOrders(String user){

            ArrayList<Order> order = DatabaseConnection.retrieve(user);
            DefaultTableModel model = (DefaultTableModel) OrdersTable.getModel();
            String[] columnNames = {"order_id","Book Name", "Cost","Status"};
            model.setColumnIdentifiers(columnNames);
            model.setRowCount(0);

            for (Order o : order) {

                model.addRow(new Object[]{
                        o.getOrder_id(),
                        o.getBookname(),
                        o.getCost(),
                        o.getStatus()});

            }

        //Allignment
        TableColumnModel column = OrdersTable.getColumnModel();
//        column.getColumn(0).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);
        column.getColumn(2).setCellRenderer(centerRenderer);
        column.getColumn(3).setCellRenderer(centerRenderer);

    }
    public static void main(String[] args) {



    }

}
