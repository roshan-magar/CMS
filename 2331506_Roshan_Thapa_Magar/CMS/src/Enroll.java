import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Enroll {
    JPanel EnrollmentPanel;
    private JTextField Name;
    private JTextField Email;
    private JComboBox<String> Course;
    private JComboBox<String> Level;
    private JButton enrollButton;
    private JCheckBox mathCheckBox;
    private JCheckBox scienceCheckBox;
    private JCheckBox englishCheckBox;
    private JCheckBox socialCheckBox;

    // Add your database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Enroll() {
        // Hide checkboxes by default
        setCheckboxVisibility(false);

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected values
                String name = Name.getText();
                String email = Email.getText();
                String course = (String) Course.getSelectedItem();
                String level = (String) Level.getSelectedItem();

                // Check how many checkboxes are selected
                int selectedCount = getSelectedCheckboxCount();

                // Insert data into the database only if exactly 2 or more checkboxes are selected
                if (selectedCount >= 2) {
                    String chooseOne = mathCheckBox.isSelected() ? "Math" : "";
                    String chooseTwo = scienceCheckBox.isSelected() ? "Science" : "";
                    String chooseThree = englishCheckBox.isSelected() ? "English" : "";
                    String chooseFour = socialCheckBox.isSelected() ? "Social" : "";

                    // Insert data into the database
                    insertData(name, email, course, level, chooseOne, chooseTwo, chooseThree, chooseFour);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select at least two modules for enrollment.");
                }
            }
        });

        Level.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show all checkboxes only if Level 6 is selected
                boolean showCheckboxes = Level.getSelectedItem().toString().equals("Level 6");
                setCheckboxVisibility(showCheckboxes);
            }
        });
    }

    private void setCheckboxVisibility(boolean isVisible) {
        mathCheckBox.setVisible(isVisible);
        scienceCheckBox.setVisible(isVisible);
        englishCheckBox.setVisible(isVisible);
        socialCheckBox.setVisible(isVisible);
    }

    private int getSelectedCheckboxCount() {
        int count = 0;
        if (mathCheckBox.isSelected()) count++;
        if (scienceCheckBox.isSelected()) count++;
        if (englishCheckBox.isSelected()) count++;
        if (socialCheckBox.isSelected()) count++;
        return count;
    }

    private void insertData(String name, String email, String course, String level, String chooseOne, String chooseTwo, String chooseThree, String chooseFour) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO enrol (name, email, course, level, chooseOne, chooseTwo, chooseThree, chooseFour) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, course);
                preparedStatement.setString(4, level);

                // Set the selected checkboxes to the corresponding columns
                preparedStatement.setString(5, chooseOne);
                preparedStatement.setString(6, chooseTwo);
                preparedStatement.setString(7, chooseThree);
                preparedStatement.setString(8, chooseFour);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Enrollment successful!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error during enrollment: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Enroll");
        frame.setContentPane(new Enroll().EnrollmentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
