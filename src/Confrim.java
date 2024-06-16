import javax.swing.*;
import java.sql.*;

public class Confrim extends OrderCheck {
    @Override
    public void decision(int id){

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            String confirm_order = "UPDATE orders SET `Status`='Order Confirmed' WHERE `order_id`= ? ;";
            PreparedStatement preparedStatement = conn.prepareStatement(confirm_order);
            preparedStatement.setInt(1, id);
            int rowsAffected= preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                        " Order is Confirmed Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to confirm",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
//             con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    };
}
