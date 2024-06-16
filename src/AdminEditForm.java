import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminEditForm extends JDialog {
    private JPanel EditingPanel;
    private JTextField tfStatus;
    private JFormattedTextField dfPrice;
    private JButton updateButton;

    public AdminEditForm(JFrame parent, String Book,double price , String status) {

        super(parent);
        setTitle("Edit");
        setContentPane(EditingPanel);
        setMinimumSize(new Dimension(1000, 750));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String stat = tfStatus.getText();
                String priceText = dfPrice.getText().trim();
                double p = Double.parseDouble(priceText);
                // Check if status or price is being edited
                boolean editStatus = !stat.isEmpty() && !stat.equals(status);
                boolean editPrice = !priceText.isEmpty() && !priceText.equals(String.valueOf(price));
                if (editStatus || editPrice) {
                    // Update the database with the new values or the existing values
                    double newPrice = editPrice ? Double.parseDouble(priceText) : price;
                    String newStatus = editStatus ? stat : status;
                    DatabaseConnection.editBook(newPrice, newStatus, Book);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter the fields or make changes",
                            "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            }
        }); setVisible(true);

    }

}
