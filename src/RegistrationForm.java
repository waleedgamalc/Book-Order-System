import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class RegistrationForm extends JDialog {
    private JTextField tfUsername;
    private JTextField tfAddress;
    private JTextField tfPhone;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JPanel RegisterPanel;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create new Account");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                registeruser();
            }
        });
        setVisible(true);
    }

    private void registeruser() {

       String Username = tfUsername.getText();
       String Password = String.valueOf(pfPassword.getPassword());
       String Address = tfAddress.getText();
       String Phone = tfPhone.getText();

       if (Username.isEmpty() || Password.isEmpty() || Address.isEmpty() || Phone.isEmpty()){

           JOptionPane.showMessageDialog(this,"Please enter all fields",
                   "Try Again",JOptionPane.ERROR_MESSAGE);
           return;
       }


        user = addUserToDatabase(Username , Password, Address, Phone);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Successfully registered new user!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public User user;
    private User addUserToDatabase(String username, String password, String address, String phone) {

        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/bookstore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO users (Username , Phone, Address, Password)" +
                    "VALUES (?,?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.username = username;
                user.Password = password;
                user.Address = address;
                user.Phone = phone;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {

        RegistrationForm registrationForm = new RegistrationForm(null);

    }
}
