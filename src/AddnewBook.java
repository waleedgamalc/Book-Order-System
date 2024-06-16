import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddnewBook extends JDialog {
    private JTextField tfTitle;
    private JTextField tfAuthor;
    private JTextField tfCategory;
    private JTextField tfStatus;
    private JButton addButton;
    private JPanel AddBookPanel;
    private JFormattedTextField pfPrice;

    public AddnewBook(JFrame parent) {

        super(parent);
        setTitle("Edit");
        setContentPane(AddBookPanel);
        setMinimumSize(new Dimension(1000, 750));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);





        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = (String) tfTitle.getText();
                String Author = tfAuthor.getText();
                String Category = tfCategory.getText();
                String priceText = pfPrice.getText().trim();
                double p = Double.parseDouble(priceText);
                String Status = tfStatus.getText();
                boolean exist = DatabaseConnection.checkExisitingOfBook(title);
                if(exist){
                    JOptionPane.showMessageDialog(null,
                            "The book is already exist",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);

                }
                else {
                    DatabaseConnection.addBook(title,Author,Category,p,Status);
                }


            }
        });setVisible(true);
    }

}
