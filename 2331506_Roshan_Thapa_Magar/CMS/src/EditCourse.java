import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditCourse {
    private JTextField ID;
    private JTextField Batch;
    private JTextField Seats;
    private JTextField CN;
    private JButton btnupdate;
    JPanel EditCoursePanel;
    private JTextField NumberOfYears;

    public EditCourse() {
        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
    }

    private void updateData() {
        String id = ID.getText();
        String batch = Batch.getText();
        String seats = Seats.getText();
        String courseName = CN.getText();
        String numberOfYears = NumberOfYears.getText();

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
            String sql = "UPDATE course SET Batch=?, Seats=?, CourseName=?, NumberOfYears=? WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, batch);
                preparedStatement.setString(2, seats);
                preparedStatement.setString(3, courseName);
                preparedStatement.setString(4, numberOfYears);
                preparedStatement.setString(5, id);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "No matching ID found or failed to update data");
                }
            }

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EditCourse");
        frame.setContentPane(new EditCourse().EditCoursePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
