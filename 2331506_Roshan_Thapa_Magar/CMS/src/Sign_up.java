import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up extends JDialog {
    private JTextField name;
    private JTextField email;
    private JTextField phone;
    private JTextField address;
    private JPasswordField password;
    private JPasswordField cpassword;
    private JComboBox<String> usertype;
    private JButton btnSignUp;
    private JButton btnCancel;
    private JPanel Sign_upPanel;
    private JComboBox comboBox1;

    public Sign_up(Log_in parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(Sign_upPanel);
        setSize(500, 550);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnSignUp.addActionListener(e -> registerUser());
        btnCancel.addActionListener(e -> dispose());


        comboBox1.setVisible(false);
        usertype.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUserType = (String) usertype.getSelectedItem();

                // Show/hide the course label and JComboBox based on the user type
                comboBox1.setVisible("Student".equals(selectedUserType));
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String Name = name.getText();
        String email = this.email.getText();
        String phone = this.phone.getText();
        String address = this.address.getText();
        String password = String.valueOf(this.password.getPassword());
        String confirmPassword = String.valueOf(cpassword.getPassword());
        String selectedUserType = (String) usertype.getSelectedItem();

        if (Name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Confirm Password does not match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email address", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isStrongPassword(password)) {
            JOptionPane.showMessageDialog(this, "Weak password. Include numbers, symbols, and both uppercase and lowercase letters.", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        addUserToDatabase(Name, email, phone, address, password, selectedUserType);
    }

    public  boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isStrongPassword(String password) {
        // Implement your own logic for password strength check
        // Example: At least 8 characters, including numbers, symbols, and both uppercase and lowercase letters
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return password.matches(passwordRegex);
    }
    private void addUserToDatabase(String name, String email, String phone, String address, String password, String userType) {
        final String DB_URL = "jdbc:mysql://localhost/cms2331506?verTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String insertSql;
            if ("Student".equals(userType)) {
                insertSql = "INSERT INTO student(Name, Email, Phone, Address, Password, Course) VALUES (?, ?, ?, ?, ?, ?)";
            } else if ("Admin".equals(userType)) {
                insertSql = "INSERT INTO admin(Name, Email, Phone, Address, Password) VALUES (?, ?, ?, ?, ?)";
            } else if ("Instructor".equals(userType)) {
                insertSql = "INSERT INTO instructor(Name, Email, Phone, Address, Password) VALUES (?, ?, ?, ?, ?)";
            } else {
                JOptionPane.showMessageDialog(this, "Invalid user type", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, password);

                // Assuming Course is a string; modify this if Course is an object
                if ("Student".equals(userType)) {
                    String selectedCourse = (String) comboBox1.getSelectedItem();
                    preparedStatement.setString(6, selectedCourse);
                }

                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Sign_up(null);
        });
    }
}
