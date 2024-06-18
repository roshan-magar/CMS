import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTutor {
    private JTextField TID;
    private JButton TDelete;
    JPanel deletePanel;

    public DeleteTutor() {
        TDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTutor();
            }
        });
    }

    private void deleteTutor() {
        String TutorId = TID.getText();

        if (TutorId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Tutor ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cms2331506?serverTimezone=UTC", "root", "");

            // Prepare the SQL statement
            String sql = "DELETE FROM tutor WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, TutorId);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Tutor deleted successfully");
                    // Optionally, you can refresh the Tutor  table after deletion
                    // You may need to pass the reference to the table or use a callback method to refresh it
                } else {
                    JOptionPane.showMessageDialog(null, "Tutor not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting Tutor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete Tutor ");
        DeleteTutor deleteTutor = new DeleteTutor();
        frame.setContentPane(deleteTutor.deletePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
