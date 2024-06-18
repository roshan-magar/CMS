import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStudent {
    private JTextField SID;
    private JButton SDelete;
    JPanel deletePanel;

    public DeleteStudent() {
        SDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }

    private void deleteStudent() {
        String StudentId = SID.getText();

        if (StudentId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Student ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cms2331506?serverTimezone=UTC", "root", "");

            // Prepare the SQL statement
            String sql = "DELETE FROM student WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, StudentId);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Student deleted successfully");
                    // Optionally, you can refresh the student table after deletion
                    // You may need to pass the reference to the table or use a callback method to refresh it
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting Student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete Student ");
        DeleteStudent deleteStudent = new DeleteStudent();
        frame.setContentPane(deleteStudent.deletePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
