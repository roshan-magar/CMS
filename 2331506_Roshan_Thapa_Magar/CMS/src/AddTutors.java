import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTutors extends JFrame{
    JPanel AddPanel;
    private JTextField TN;
    private JTextField EL;
    private JTextField PE;
    private JButton button1;

    public AddTutors() {


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
    }
    private void insertData() {
        String TutorName = TN.getText();
        String Email = EL.getText();
        String Phone = PE.getText();

        // JDBC connection parameters
        String url = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
        String username = "root";
        String password = "";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String sql = "INSERT INTO tutor (TutorName, Email, Phone) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, TutorName);
                preparedStatement.setString(2, Email);
                preparedStatement.setString(3, Phone);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data inserted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to insert data");
                }
            }

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AddTutors");
        frame.setContentPane(new  AddTutors().AddPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
