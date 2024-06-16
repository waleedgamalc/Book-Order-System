import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class BuyForm extends JDialog {
    private JTable buyTable;
    private JFormattedTextField ffTotal;
    private JButton btnCash;
    private JButton btnCC;
    private JPanel BuyPanel;

    public BuyForm(JFrame parent,String user) {

        super(parent);
        setTitle("Buy");
        setContentPane(BuyPanel);
        setMinimumSize(new Dimension(1000, 700));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        double totalPayment =showOrders(user);
        ffTotal.setValue(totalPayment);

        setVisible(true);
    }
    private double showOrders(String user){

        double Total =0;
        ArrayList<Order> order = DatabaseConnection.retrieve(user);
        DefaultTableModel model = (DefaultTableModel) buyTable.getModel();
        String[] columnNames = {"Book Name", "Cost","Status"};
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);

        for (Order o : order) {

            if (o.getStatus().equals("Order Confirmed")){
                Total = Total + o.getCost();
                model.addRow(new Object[]{
                        o.getBookname(),
                        o.getCost(),
                        o.getStatus()});
            }
            else{continue;}



        }

        //Allignment
        TableColumnModel column = buyTable.getColumnModel();
//        column.getColumn(0).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);
        column.getColumn(2).setCellRenderer(centerRenderer);
        return Total;
    }
}
