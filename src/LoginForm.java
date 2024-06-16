import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JFrame;

public class LoginForm extends JDialog {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JPanel loginpanel;
    private JButton newUserButton;


    public LoginForm(JFrame parent) {

        super(parent);
        setTitle("Login");
        setContentPane(loginpanel);
        setMinimumSize(new Dimension(500,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String username = tfUsername.getText();
              String password = String.valueOf(pfPassword.getPassword());
              if (username.equals("admin") && password.equals("admin")){
                  AdminForm a = new AdminForm(parent);
                  setVisible(false);
              }
              else {
                  user = getAuthenticatedUSer(username, password);
                  if (user != null) {
                      setVisible(false);
                      userDashboardFrom u = new userDashboardFrom(parent, user.username);
                      u.setVisible(true);


                  } else {
                      JOptionPane.showMessageDialog(LoginForm.this,
                              "Email or Password Invalid",
                              "Try again",
                              JOptionPane.ERROR_MESSAGE);
                  }
              }
            }
        });

        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(null);
            }
        });
        setVisible(true);
    }

    public User user;
    private User getAuthenticatedUSer(String username, String password) {
        User user = null;


        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM users  WHERE Username=? AND Password=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.username = resultSet.getString("Username");
                user.Phone = resultSet.getString("Phone");
                user.Address = resultSet.getString("Address");
                user.Password = resultSet.getString("Password");
            }
                stmt.close();
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }


            return user;
        }



        public static void main(String[] args) {
       LoginForm l = new LoginForm(null);


    }
}

