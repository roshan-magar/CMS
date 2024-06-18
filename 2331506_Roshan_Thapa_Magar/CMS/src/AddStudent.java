import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudent {
    private JTextField PE;
    private JTextField SN;
    private JTextField CE;
    private JTextField ML;
    private JButton btnsubmit;
    JPanel AddPanel;
    private JTextField Age;
    private JComboBox ComboUser;
    private JComboBox CN;

    public AddStudent() {
        btnsubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
    }
    private void insertData() {
        String StudentName = SN.getText();
        String Email = ML.getText();
        String Phone = PE.getText();
        String Course =  (String) CN.getSelectedItem();
        int studentAge = Integer.parseInt(Age.getText());
        String gender = (String) ComboUser.getSelectedItem();
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
            String sql = "INSERT INTO studentdetails (Name, Email, Phone,Age,Gender,Course) VALUES (?, ?, ?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, StudentName);
                preparedStatement.setString(2, Email);
                preparedStatement.setString(3, Phone);
                preparedStatement.setInt(4, studentAge);
                preparedStatement.setString(5, gender);
                preparedStatement.setString(6, Course);

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
        frame.setContentPane(new  AddStudent().AddPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
