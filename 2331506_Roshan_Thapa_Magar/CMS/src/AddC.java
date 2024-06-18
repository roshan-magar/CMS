import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddC {
    private JTextField BH;
    private JTextField NOY;
    private JTextField CN;
    private JButton btnsubmit;
    private JTextField ST;
    JPanel addPanel;

    public AddC() {
        btnsubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
    }
    private void insertData() {
        String courseName = CN.getText();
        String seats = ST.getText();
        String batch = BH.getText();
        String numberOfYears = NOY.getText();

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
            String sql = "INSERT INTO course (CourseName, Seats, Batch, NumberOfYears) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, courseName);
                preparedStatement.setString(2, seats);
                preparedStatement.setString(3, batch);
                preparedStatement.setString(4, numberOfYears);

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
        JFrame frame = new JFrame("AddCourse");
        frame.setContentPane(new AddC().addPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
