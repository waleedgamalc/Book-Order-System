import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminOrders extends JDialog {
    private JPanel manageOrderPanel;
    private JTable manageOrderTablee;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JScrollPane manageOrderTable;
    public AdminOrders(JFrame parent) {

        super(parent);
        setTitle("Manage");
        setContentPane(manageOrderPanel);
        setMinimumSize(new Dimension(1000, 700));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createTable();


        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = manageOrderTablee.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select an Order to cancel.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id =  (int) manageOrderTablee.getValueAt(selectedRow, 0);
                OrderCheck o = DecisionFactory.getDecision("Order Confirmed");
                o.decision(id);
                createTable();


            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = manageOrderTablee.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select an Order to cancel.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id =  (int) manageOrderTablee.getValueAt(selectedRow, 0);
                OrderCheck o = DecisionFactory.getDecision("Cancelled");
                o.decision(id);
                createTable();

            }
        });setVisible(true);
    }
    private void createTable() {


        DefaultTableModel model = (DefaultTableModel)manageOrderTablee.getModel();
        ArrayList<Order> all_orders = DatabaseConnection.showallOrders();
        String[] columnNames = {"order_id", "User", "Book Title", "Price" ,"Status"};
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        for(Order b : all_orders) {

            model.addRow(new Object[]{
                    b.getOrder_id(),
                    b.getUser(),
                    b.getBookname(),
                    b.getCost(),
                    b.getStatus()});

        }


        // Allignment
        TableColumnModel column = manageOrderTablee.getColumnModel();
        column.getColumn(0).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);
        column.getColumn(2).setCellRenderer(centerRenderer);
        column.getColumn(3).setCellRenderer(centerRenderer);
        column.getColumn(4).setCellRenderer(centerRenderer);

    }
}
