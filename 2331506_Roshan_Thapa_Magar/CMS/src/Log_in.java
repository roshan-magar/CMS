import javax.swing.*;
import java.awt.*;
import java.sql.*;
/*------------------------------------------Roshan Thapa Magar-----------------------------------------------------*/
public class Log_in extends JDialog {
    private JTextField Email;
    private JPasswordField Password;
    private JComboBox<String> User;
    private JButton btnlogin;
    private JButton btnsignup;
    private JPanel LoginPanel;


    public Log_in(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setPreferredSize(new Dimension(330, 355));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnlogin.addActionListener(e -> authenticateUser());
        btnsignup.addActionListener(e -> openSignUp());

        pack(); // Adjust the size of the dialog to its content
        setVisible(true);
    }


    /*-------------------------------------------Initialize ----------------------------------------------------*/
    private void initializeComponents() {
        LoginPanel = new JPanel();
        LoginPanel.setLayout(new GridLayout(4, 2, 5, 5));

        Email = new JTextField();
        Password = new JPasswordField();
        User = new JComboBox<>(new String[]{"Student", "Admin","Instructor"});
        btnlogin = new JButton("Login");
        btnsignup = new JButton("Sign Up");

        // Add components to the panel with labels
        addRowToPanel(LoginPanel, "Email:", Email);
        addRowToPanel(LoginPanel, "Password:", Password);
        addRowToPanel(LoginPanel, "User Type:", User);
        addRowToPanel(LoginPanel, "", btnsignup);
        addRowToPanel(LoginPanel, "", btnlogin);
    }

    private void addRowToPanel(JPanel panel, String label, JComponent component) {
        panel.add(new JLabel(label));
        panel.add(component);
    }

    private void authenticateUser() {
        String email = Email.getText();
        String password = String.valueOf(Password.getPassword());
        String userType = (String) User.getSelectedItem();
        System.out.println("Your type"+userType);

        Student user = getAccount(email, password, userType);

        if (user != null) {
            displayUserInfo(user);
            loggedInUser = user.getName();
            dispose();
            openMainBar(user,userType);
            displayUserInfo(user);
        } else {
            showErrorMessage("Email or Password invalid", "Try Again");
        }
    }
    private void openMainBar(Student user,String userType) {
        JFrame frame = new JFrame("Main bar");
        Main_Bar mainBar = new Main_Bar(frame,userType);//user,userType
        frame.setContentPane(mainBar.MainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Populate tables with data
        mainBar.displaydatabaseTable("course", mainBar.table2);
        mainBar.displaydatabaseTable("tutor", mainBar.table3);
        mainBar.displaydatabaseTable("student", mainBar.table4);
    }

    private Student getAccount(String email, String password, String userType) {
        Student user = null;
        /*--------------------------------------------Database Connection ---------------------------------------------------*/
        final String DB_URL = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        /*---------------------------------------------------------------------------------------------*/
        String sql = "SELECT * FROM " + userType + " WHERE Email = ? AND Password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Student();
                    user.setName(resultSet.getString("Name"));
                    user.setEmail(resultSet.getString("Email"));
                    user.setPhone(resultSet.getLong("Phone"));
                    user.setAddress(resultSet.getString("Address"));
                    user.setPassword(resultSet.getString("Password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private void openSignUp() {
        // Create and display the Registration
        Sign_up signUp = new Sign_up(this);
        signUp.setVisible(true);
    }


    private void displayUserInfo(Student user) {
        System.out.println("Successful Authentication of: " + user.getName());
    }
    private String loggedInUser;

    public void login(String username, String password) {
        loggedInUser = username;
    }

    public String getLoggedInUser() {

        return loggedInUser;
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Log_in loginForm = new Log_in(null);
        });
    }
}
