import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCourse {
    private JTextField CID;
    private JButton CDelete;
    JPanel deletePanel;

    public DeleteCourse() {
        CDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
            }
        });
    }

    private void deleteCourse() {
        String courseId = CID.getText();

        if (courseId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Course ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cms2331506?serverTimezone=UTC", "root", "");

            // Prepare the SQL statement
            String sql = "DELETE FROM course WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, courseId);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Course deleted successfully");
                    // Optionally, you can refresh the course table after deletion
                    // You may need to pass the reference to the table or use a callback method to refresh it
                } else {
                    JOptionPane.showMessageDialog(null, "Course not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete Course Example");
        DeleteCourse deleteCourse = new DeleteCourse();
        frame.setContentPane(deleteCourse.deletePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
