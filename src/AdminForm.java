import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminForm extends JDialog {
    private JTable AdminTablee;
    private JButton deleteBookButton;
    private JButton addBookButton;
    private JButton editBookButton;
    private JScrollPane AdminTable;
    private JPanel AdminPanel;
    private JComboBox AdmincatCombo;
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;
    private JTextArea tfCategory;
    private JButton theOrdersButton;
    private JButton statisticsButton;


    public AdminForm(JFrame parent) {

        super(parent);
        setTitle("Login");
        setContentPane(AdminPanel);
        setMinimumSize(new Dimension(1000, 750));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

//        userDashboardFrom u = new userDashboardFrom(parent,null);
//        u.setVisible(false);
//        AdmincatCombo = u.getComboBox();

        ArrayList<String> s = DatabaseConnection.allCategories();
               for (String m : s){
                    AdmincatCombo.addItem(m);
                }
        createTable();

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = AdminTablee.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select a Book to delete.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String Book = (String) AdminTablee.getValueAt(selectedRow, 0);
                DatabaseConnection.deleteBook(Book);
                createTable();

            }
        });
        editBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = AdminTablee.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null
                            , "Please select a Book to delete.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String Book = (String) AdminTablee.getValueAt(selectedRow, 0);
                double price = (double) AdminTablee.getValueAt(selectedRow, 3);
                String status = (String) AdminTablee.getValueAt(selectedRow, 4);

                AdminEditForm a = new AdminEditForm(parent,Book,price,status);
                createTable();
            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddnewBook add = new AddnewBook(parent);
                createTable();
            }
        });
//        AdmincatCombo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ArrayList<String> s = DatabaseConnection.allCategories();
//                for (String m : s){
//                    AdmincatCombo.addItem(m);
//                }
//            }
//        });
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cat =tfCategory.getText();
                if (cat.isEmpty()){
                    JOptionPane.showMessageDialog(null
                            , "Please Type a Category to add.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    DatabaseConnection.addCat(cat);
                }


            }
        });
        deleteCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cat =tfCategory.getText();
                if (cat.isEmpty()){
                    JOptionPane.showMessageDialog(null
                            , "Please Type a Category to delete.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    DatabaseConnection.deleteCat(cat);
                }


            }
        });
        theOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminOrders a = new AdminOrders(parent);
            }
        });
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    SalesForm s = new SalesForm(parent);

            }
        });setVisible(true);
    }
    private void createTable() {


        DefaultTableModel model = (DefaultTableModel)AdminTablee.getModel();
        ArrayList<Book> all_books = DatabaseConnection.showAllBooks();
        String[] columnNames = {"Title", "Author", "Category", "Price","Status"};
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
        TableColumnModel column = AdminTablee.getColumnModel();
        column.getColumn(0).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);
        column.getColumn(2).setCellRenderer(centerRenderer);
        column.getColumn(3).setCellRenderer(centerRenderer);
        column.getColumn(4).setCellRenderer(centerRenderer);

    }


    public static void main(String[] args) {

        AdminForm a = new AdminForm(null);
    }


}
