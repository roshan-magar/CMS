import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteResult {
    private JTextField SearchTexField;
    private JCheckBox okCheckBox;
    private JButton btnDelete;
    JPanel DeleteandUpdatePanel;
    private JTextField ID;

    private static final String URL = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public DeleteResult() {
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String module = SearchTexField.getText();
                String studentID = ID.getText();

                if (okCheckBox.isSelected()) {
                    boolean isIDFilled = !studentID.trim().isEmpty();
                    boolean isModuleFilled = !module.trim().isEmpty();

                    if (!isIDFilled && !isModuleFilled) {
                        JOptionPane.showMessageDialog(null, "ID and Module are not filled.");
                        return;
                    } else if (!isIDFilled) {
                        JOptionPane.showMessageDialog(null, "ID is not filled.");
                        return;
                    } else if (!isModuleFilled) {
                        JOptionPane.showMessageDialog(null, "Module is not filled.");
                        return;
                    }

                    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String selectQuery = "SELECT * FROM studentresult WHERE Module = ? AND ID = ?";
                        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                            selectStatement.setString(1, module);
                            selectStatement.setString(2, studentID);

                            ResultSet resultSet = selectStatement.executeQuery();

                            if (resultSet.next()) {
                                // Both ID and Module matched
                                String deleteQuery = "DELETE FROM studentresult WHERE Module = ? AND ID = ?";
                                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                                    deleteStatement.setString(1, module);
                                    deleteStatement.setString(2, studentID);

                                    int rowsAffected = deleteStatement.executeUpdate();

                                    if (rowsAffected > 0) {
                                        JOptionPane.showMessageDialog(null, "Row deleted successfully!");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Error deleting row.");
                                    }
                                }
                            } else {
                                // Check which one did not match
                                if (!isIDFilled) {
                                    JOptionPane.showMessageDialog(null, "Module matched, but ID did not match.");
                                } else if (!isModuleFilled) {
                                    JOptionPane.showMessageDialog(null, "ID matched, but Module did not match.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "ID and Module did not match.");
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle SQLException appropriately
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please check the checkbox before deleting.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Delete Result");
        frame.setContentPane(new DeleteResult().DeleteandUpdatePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
