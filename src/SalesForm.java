import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class SalesForm extends JDialog {
    private JPanel SalesPanel;
    private JTable SalesTablee;
    private JScrollPane salesTable;

    public SalesForm(JFrame parent) {

        super(parent);
        setTitle("Sales");
        setContentPane(SalesPanel);
        setMinimumSize(new Dimension(1000, 800));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);




        createTable();
        setVisible(true);



    }

    private void createTable() {


        DefaultTableModel model = (DefaultTableModel) SalesTablee.getModel();
        ArrayList<Statistics> s = DatabaseConnection.showSales();
        String[] columnNames = {"Book Title", "Sales"};
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        for (Statistics b : s) {

            model.addRow(new Object[]{
                    b.getTitle(),
                    b.getOccurences()});


        }



        // Allignment
        TableColumnModel column = SalesTablee.getColumnModel();
        column.getColumn(0).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);

    }
}
