import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Module {
    private JTextField MID;
    private JTextField MName;
    private JComboBox YearcomboBox;
    private JTextField TN;
    private JTextField ML;
    private JButton ADDButton;
    private JButton deleteButton;
    private JButton cancelButton;
    JPanel ModulePanel;
    private JComboBox LevelcomboBox;
    class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }


    public Module() {
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addModule();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(ModulePanel).dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteModule();
            }
        });
    }
    private void addModule() {
        if (validateInput()) {
            String url = "jdbc:mysql://localhost:3306/cmS2331506";
            String username = "root";
            String password = "";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO modules (MID, MName, academic_year,Tutor, module_level, module_leader) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, MID.getText());
                    statement.setString(2, MName.getText());
                    statement.setString(3, YearcomboBox.getSelectedItem().toString());
                    statement.setString(4, TN.getText());
                    statement.setString(5, LevelcomboBox.getSelectedItem().toString());
                    statement.setString(6, ML.getText());

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Module added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add module.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new ValidationException("Please fill in all the required fields.");
        }
    }

    private void deleteModule() {
        String moduleId = JOptionPane.showInputDialog(null, "Enter Module ID to delete:");
        if (moduleId != null && !moduleId.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this module?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String url = "jdbc:mysql://localhost:3306/cmS2331506";
                String username = "root";
                String password = "";

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    String sql = "DELETE FROM modules WHERE MID = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, Integer.parseInt(moduleId));
                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(null, "Module deleted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete module.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private boolean validateInput() {
        return !MID.getText().isEmpty() &&
                !MName.getText().isEmpty() &&
                YearcomboBox.getSelectedItem() != null &&
                !TN.getText().isEmpty() &&
                LevelcomboBox.getSelectedItem() != null &&
                !ML.getText().isEmpty();
    }

    private void closeForm() {
        SwingUtilities.getWindowAncestor(ModulePanel).dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Module Form");
            frame.setContentPane(new Module().ModulePanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
