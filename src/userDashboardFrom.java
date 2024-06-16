import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class userDashboardFrom extends JDialog {
    private JPanel userScreen;
    public JTable booksTable;
    private  JComboBox categoryCombo;
    private JButton CurrentOrders;
    private JButton btnAddToCart;
    private JButton btnViewReview;
    private JButton btnConvertEGP;
    private JFormattedTextField ffEGP;
    public JLabel userlabel;

//    public  JComboBox<String> getComboBox() {
//        return categoryCombo;
//    }

    public userDashboardFrom(JFrame parent, String username) {


        super(parent);
        setTitle("userDashborad");
        setContentPane(userScreen);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ArrayList<String> s = DatabaseConnection.allCategories();
        for (String m : s){
            categoryCombo.addItem(m);
        }

        createTable();

        categoryCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()== ItemEvent.SELECTED){

                    showBooks();

                }
            }
        });
        btnAddToCart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select a Book to add to the cart.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }

                order.setUser(username);
                order.setBookname((String) booksTable.getValueAt(selectedRow, 0));
                order.setCost((double) booksTable.getValueAt(selectedRow, 3));
                order.setStatus("Processing");

                DatabaseConnection.addToDatabase(order);


            }
        });
        CurrentOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrdersForm o = new OrdersForm(parent,username);
            }
        });

        btnViewReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select a Book to add to the cart.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String Book = (String) booksTable.getValueAt(selectedRow, 0);
                ReviewsFrom r = new ReviewsFrom(parent,Book);

            }
        });
        btnConvertEGP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select a Book.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
               double UScost = (double) booksTable.getValueAt(selectedRow, 3);
               DollarValue dollarValue = new DollarValue(UScost);
                // Create an adapter to convert DollarValue to EGPValue
                EGPValue adapter = new DollarToEGPAdapter(dollarValue);
                ffEGP.setValue(adapter.getEGP());


            }
        });setVisible(true);
    }


    Order order = new Order();
    Review review = new Review();


private void showBooks(){
    String categ = (String)categoryCombo.getSelectedItem();
    if ("All".equals(categ)){
        createTable();
    }
    else {
        ArrayList<Book> bookat = DatabaseConnection.showCategorizedBooks(categ);
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);

        for (Book b : bookat) {

            model.addRow(new Object[]{
                    b.getTitle(),
                    b.getAuthor(),
                    b.getCategory(),
                    b.getPrice(),
                    b.getStatus()});

        }
    }
}


    private void createTable() {


        DefaultTableModel model = (DefaultTableModel)booksTable.getModel();
        ArrayList<Book> all_books = DatabaseConnection.showAllBooks();
        String[] columnNames = {"Title", "Author", "Category", "Price" ,"Status"};
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        for(Book b : all_books) {

            model.addRow(new Object[]{
                    b.getTitle(),
                    b.getAuthor(),
                    b.getCategory(),
                    b.getPrice(),
                    b.getStatus()});

        }


        // Allignment
        TableColumnModel column = booksTable.getColumnModel();
        column.getColumn(0).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);
        column.getColumn(2).setCellRenderer(centerRenderer);
        column.getColumn(3).setCellRenderer(centerRenderer);
        column.getColumn(4).setCellRenderer(centerRenderer);

    }



//    public static void main(String[] args) {
////        userDashboardFrom u = new userDashboardFrom();
//
//
//    }


}
