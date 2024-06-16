import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class ReviewsFrom extends JDialog {
    private JTable bookReviewsTable;
    private JPanel ReviewsPanel;

    public ReviewsFrom(JFrame parent, String book) {
        super(parent);
        setTitle("bookReview");
        setContentPane(ReviewsPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        showReviews(book);


        setVisible(true);



    }


    private void showReviews(String book){


            ArrayList<Review> reviews = DatabaseConnection.retrieveReviews(book);
            DefaultTableModel model = (DefaultTableModel) bookReviewsTable.getModel();
            String[] columnNames = {"User", "Book Title","Review"};
            model.setColumnIdentifiers(columnNames);
            model.setRowCount(0);

            for (Review b : reviews) {

                model.addRow(new Object[]{
                        b.getUsername(),
                        b.getBook(),
                        b.getReview()});



            }
        // Allignment
        TableColumnModel column = bookReviewsTable.getColumnModel();
        column.getColumn(2).setMinWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.getColumn(0).setCellRenderer(centerRenderer);
        column.getColumn(1).setCellRenderer(centerRenderer);
        column.getColumn(2).setCellRenderer(centerRenderer);

    }
}
