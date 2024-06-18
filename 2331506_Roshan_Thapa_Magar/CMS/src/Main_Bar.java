import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class Main_Bar extends JFrame {
    /*--------------------------------------------------Main Panel start----------------------------*/
    JPanel MainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel btnPanel;
    private JButton btnDB;
    private JButton btnLT;
    private JButton btnCS;
    private JButton btnTS;
    private JButton btnSS;
    private JButton btnML;
    private JButton btnSG;
    /*--------------------------------Main Panel END----------------------------------------*/


    /*----------------------------Course window---------------------------*/
    private JTextField CText;
    JTable table2;
    private JButton CDelete;
    private JButton CAdd;
    private JButton CEdit;

    /*---------------------------------Tutors Window-------------------------*/
    private JTextField Ttext;
    JTable table3;
    private JButton TDelete;
    private JButton TEdit;
    private JButton TAdd;

    /*--------------------------------------Student window------------------------*/
    private JTextField SText;
    JTable table4;
    private JButton SDelete;
    private JButton Sview;
    private JButton SAdd;
    private JButton createAStudentReportButton;
    private JButton changePasswordButton;
    private JTable table1;
    private JLabel TotalCourse;
    private JLabel TotalTeacher;
    private JLabel TotalStudent;
    private JButton TotalC;
    private JButton TotalT;
    private JButton TotalS;
    private JButton btnRefferess;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton editProfileButton;
    private JPasswordField OldPassword;
    private JPasswordField NewPassword;
    private JComboBox combo;
    private JButton btnChange;
    private JButton moduleButton;
    private JTable table5;
    private JButton moduleButton1;
    private JButton enrollmentButton;
    private JTable table6;
    private JButton enrol;


    /*----------------------------------------Change Password ------------------------------------------------------*/
    private static final String DB_URL = "jdbc:mysql://localhost/cms2331506";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void ChangePassword() {
        String selectedUserType = (String) combo.getSelectedItem();
        char[] oldPasswordChars = OldPassword.getPassword();
        String oldPassword = new String(oldPasswordChars);
        char[] newPasswordChars = NewPassword.getPassword();
        String newPassword = new String(newPasswordChars);

        if (changePassword(selectedUserType, oldPassword, newPassword)) {
            JOptionPane.showMessageDialog(null, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect old password or an error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean changePassword(String userType, String oldPassword, String newPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String tableName = getTableName(userType);
            String updateQuery = "UPDATE " + tableName + " SET password = ? WHERE Password = ?";

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, oldPassword);

                int rowsAffected = updateStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String getTableName(String userType) {
        switch (userType) {
            case "Student":
                return "student";
            case "Admin":
                return "admin";
            case "Instructor":
                return "instructor";
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    private void clearFields() {
        OldPassword.setText("");
        NewPassword.setText("");
    }
    /*-------------------------Hide buttons not needed for multiple user type --------------------------*/
    private void customizeUIForUserRole(String userType) {

        if ("Student".equals(userType)) {
            // Hide buttons not needed for students
            CAdd.setVisible(false);
            CDelete.setVisible(false);
            CEdit.setVisible(false);
            TAdd.setVisible(false);
            TDelete.setVisible(false);
            TEdit.setVisible(false);
            SAdd.setVisible(false);
            SDelete.setVisible(false);
            createAStudentReportButton.setVisible(false);
            moduleButton1.setVisible(false);
            TotalC.setVisible(false);
            TotalT.setVisible(false);
            TotalS.setVisible(false);


        } else if ("Admin".equals(userType)) {
            TotalC.setVisible(false);
            TotalT.setVisible(false);
            TotalS.setVisible(false);

        } else if ("Instructor".equals(userType)) {
            CAdd.setVisible(false);
            CDelete.setVisible(false);
            CEdit.setVisible(false);
            TAdd.setVisible(false);
            TDelete.setVisible(false);
            TEdit.setVisible(false);
            SAdd.setVisible(false);
            SDelete.setVisible(false);
            TotalC.setVisible(false);
            TotalT.setVisible(false);
            TotalS.setVisible(false);
            enrol.setVisible(false);
        }
    }


    /*------------------------------------ Main panel btn action----------------------*/

    public static class DatabaseManager extends Main_Bar {
        /*-------------------------------------Databse Connection-----------------------------------------*/
        private static final String URL = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "";

        private static Connection connection;

        public DatabaseManager(JFrame parent) {
            super(parent);
        }



        /*---------------------------------Get a singleton instance of the database connection--------------------------*/
        public static Connection getConnection() throws SQLException {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
            return connection;
        }
    }
    /*------------------Find Login user type---------------------*/
    private String userType;
    public Main_Bar(JFrame parent,String userType) {
        JFrame mainbar = new JFrame("Main Bar");
        mainbar.pack();
        mainbar.setLocationRelativeTo(this);
        mainbar.setVisible(true);
        this.userType = userType;
        customizeUIForUserRole(userType);
        System.out.println("Your user type is "+userType);

        /*-----------------------------------DashBoard Button action Listener------------------------------*/
        btnDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tabbedPane1.setSelectedIndex(0);
            }
        });
        /*-----------------------------------Course Button action Listener------------------------------*/
        btnCS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tabbedPane1.setSelectedIndex(1);
            }
        });
        /*-----------------------------------Tutors Button action Listener------------------------------*/
        btnTS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tabbedPane1.setSelectedIndex(2);
            }
        });
        /*-----------------------------------Students Button action Listener------------------------------*/
        btnSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tabbedPane1.setSelectedIndex(3);
            }
        });
        /*-----------------------------------Mail Button action Listener------------------------------*/
        btnML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openMailAccount();
            }
        });
        /*-----------------------------------Setting Button action Listener------------------------------*/
        btnSG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tabbedPane1.setSelectedIndex(4);
            }
        });
        /*-----------------------------------Logout Button action Listener------------------------------*/
        btnLT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showLogoutConfirmation();
            }
        });
        /*-----------------------------------add course  Button action Listener------------------------------*/
        CAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openAddCourseWindow();
            }
        });
        /*-----------------------------------Add Tutors Button action Listener------------------------------*/
        TAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openAddTutorWindow();
            }
        });
        /*-----------------------------------Add Student Button action Listener------------------------------*/
        SAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openAddStudentWindow();
            }
        });
        /*-----------------------------------Delete Course Button action Listener------------------------------*/
        CDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openDeleteCourseWindow();
            }
        });
        /*-----------------------------------Delete Student Button action Listener------------------------------*/
        SDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openDeleteStudentWindow();
            }
        });
        /*-----------------------------------Delete Tutors Button action Listener------------------------------*/
        TDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openDeleteTutorWindow();
            }
        });
        /*-----------------------------------Edit Course Button action Listener------------------------------*/
        CEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openUpdateCourseWindow();
            }
        });
        /*-----------------------------------Edit Tutors Button action Listener------------------------------*/
        TEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openUpdateTutorWindow();
            }
        });
        /*----------------------------------- Student Result view------------------------------*/
        Sview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewProgessWindoe();
            }
        });
        /*-----------------------------------Result update Button action Listener------------------------------*/
        createAStudentReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInsertStudentResultWidow();
            }
        });

        /*-----------------------------------Total Course Button action Listener------------------------------*/
        TotalC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalCourseCount = getTotalCourseCount();
                TotalCourse.setText(String.valueOf(totalCourseCount));
            }
        });
        /*-----------------------------------Total Teacher Button action Listener------------------------------*/
        TotalT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalTutorCount = getTotalTutorCount();
                TotalTeacher.setText(String.valueOf(totalTutorCount));
            }
        });
        /*-----------------------------------Total Student Button action Listener------------------------------*/
        TotalS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalStudentCount = getTotalStudentCount();
                TotalStudent.setText(String.valueOf(totalStudentCount));
            }
        });
        /*----------------------------------- Button action Listener------------------------------*/
        btnRefferess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });
        /*-----------------------------------Module overvew Button action Listener------------------------------*/
        moduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane1.setSelectedIndex(5);
            }
        });
        /*--------------------------------------Open Module window--------------------------------------------------------*/
        moduleButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openmoduleWindow();
            }
        });
        /*--------------------------------------Open enrol Panel--------------------------------------------------------*/
        enrollmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane1.setSelectedIndex(6);
            }
        });
        /*--------------------------------------Open enrol window--------------------------------------------------------*/
        enrol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showenrolWindow();
            }
        });

        /*-----------------------------------Change password Button action Listener------------------------------*/
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassword();
            }
        });
        CText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable("course", CText.getText(), table2);
            }
        });
        Ttext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable("tutor", Ttext.getText(), table3);
            }
        });
        SText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable("student", SText.getText(), table4);
            }
        });
    }

    public Main_Bar(JFrame parent) {
        super(parent.getGraphicsConfiguration());
        setTitle("Login");
        setContentPane(MainPanel);
        setPreferredSize(new Dimension(400, 320));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);}
    /*-----------------------------------------------------------------*/
    private void showLogoutConfirmation() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Successfully logged out");
            System.exit(0);
        }
    }
    private void filterTable(String tableName, String searchText, JTable table) {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + tableName + "`")) {

            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                boolean match = false;
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    if (value != null && value.toLowerCase().startsWith(searchText.toLowerCase())) {
                        match = true;
                        break;
                    }
                }
                if (match) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getObject(i);
                    }
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /*-----------------refreshData-----------*/
    private void refreshData() {

        // Refresh the tables
        displaydatabaseTable("course", table2);
        displaydatabaseTable("tutor", table3);
        displaydatabaseTable("studentdetails", table4);
        displaydatabaseTable("modules",table5);
        displaydatabaseTable("enrol",table6);
        displaydatabaseTable("student",table1);



        //Recalculate and update counts
        countRows("course");
        countRows("tutor");
        countRows("studentdetails");

        // Update total counts labels
        int totalCourseCount = getTotalCourseCount();
        int totalTutorCount = getTotalTutorCount();
        int totalStudentCount = getTotalStudentCount();

        TotalCourse.setText(String.valueOf(totalCourseCount));
        TotalTeacher.setText(String.valueOf(totalTutorCount));
        TotalStudent.setText(String.valueOf(totalStudentCount));
    }
    /*-----------------------------------mail------------------------------*/
    private void openMailAccount() {
        try {
            String mailAccountURL = "https://mail.google.com/mail/u/0/#inbox";
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(mailAccountURL));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    private void setModal(boolean b) {
    }
    /*----------------------------------- enroll  ------------------------------*/
    private void showenrolWindow(){
        JFrame enroll = new JFrame("Enrol");
        enroll.setContentPane(new Enroll().EnrollmentPanel);
        enroll.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enroll.pack();
        enroll.setLocationRelativeTo(this);
        enroll.setVisible(true);
    }
    /*----------------------------------- add Course function ------------------------------*/
    private void openAddCourseWindow() {
        JFrame addCourseFrame = new JFrame("Add Course");
        addCourseFrame.setContentPane(new AddC().addPanel);
        addCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCourseFrame.pack();
        addCourseFrame.setLocationRelativeTo(this);
        addCourseFrame.setVisible(true);
    }
    /*----------------------------------- add Tutor function ------------------------------*/
    private void openAddTutorWindow() {
        JFrame addTutorFrame = new JFrame("Add Tutor");
        addTutorFrame.setContentPane(new AddTutors().AddPanel);
        addTutorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addTutorFrame.pack();
        addTutorFrame.setLocationRelativeTo(this);
        addTutorFrame.setVisible(true);
    }

    /*-----------------------------------Add Student function------------------------------*/
    private void openAddStudentWindow() {
        JFrame addStudentFrame = new JFrame("Add student");
        addStudentFrame.setContentPane(new AddStudent().AddPanel);
        addStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addStudentFrame.pack();
        addStudentFrame.setLocationRelativeTo(this);
        addStudentFrame.setVisible(true);
    }


    /*-----------------------------------Delete Course function------------------------------*/
    private void openDeleteCourseWindow() {
        JFrame deleteCourseFrame = new JFrame("Delete Course");
        deleteCourseFrame.setContentPane(new DeleteCourse().deletePanel);
        deleteCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteCourseFrame.pack();
        deleteCourseFrame.setLocationRelativeTo(this);
        deleteCourseFrame.setVisible(true);
    }

    /*-----------------------------------Delete student btn function------------------------------*/
    private void openDeleteStudentWindow() {
        JFrame deleteStudentFrame = new JFrame("Delete student");
        deleteStudentFrame.setContentPane(new DeleteStudent().deletePanel);
        deleteStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteStudentFrame.pack();
        deleteStudentFrame.setLocationRelativeTo(this);
        deleteStudentFrame.setVisible(true);
    }


    /*-----------------------------------Delete Tutors btn function ------------------------------*/
    private void openDeleteTutorWindow() {
        JFrame addCourseFrame = new JFrame("Add Tutors");
        addCourseFrame.setContentPane(new DeleteTutor().deletePanel);
        addCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCourseFrame.pack();
        addCourseFrame.setLocationRelativeTo(this);
        addCourseFrame.setVisible(true);
    }

    /*-----------------------------------Update Course- btn function -----------------------------*/
    private void openUpdateCourseWindow() {
        JFrame cupdate = new JFrame("Update Course");
        cupdate.setContentPane(new EditCourse().EditCoursePanel);
        cupdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cupdate.pack();
        cupdate.setLocationRelativeTo(this);
        cupdate.setVisible(true);
    }
    /*-----------------------------------Update Tutor btn function------------------------------*/
    private void openUpdateTutorWindow() {
        JFrame update = new JFrame("Update Tutor");
        update.setContentPane(new EditTutor().EditTutorPanel);
        update.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        update.pack();
        update.setLocationRelativeTo(this);
        update.setVisible(true);
    }

    /*-----------------------------------Student Result btn function ------------------------------*/
    private void openViewProgessWindoe() {
        JFrame view = new JFrame("Result Student");
        view.setContentPane(new Result().ResultPanel);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.pack();
        view.setLocationRelativeTo(this);
        view.setVisible(true);
    }

    /*-----------------------------------Result ------------------------------*/
    private void openInsertStudentResultWidow(){
        JFrame studentresult = new JFrame("Result Student");
        studentresult.setContentPane(new InsertResult().ResultFormPanel);
        studentresult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentresult.pack();
        studentresult.setLocationRelativeTo(this);
        studentresult.setVisible(true);
    }

    /*-----------------------------------Change Password------------------------------*/
    public void openmoduleWindow(){
        JFrame module= new JFrame("Module");
        module.setContentPane(new Module().ModulePanel);
        module.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        module.pack();
        module.setLocationRelativeTo(this);
        module.setVisible(true);
    }

    private static int totalCourseCount = 0;
    private static int totalStudentCount = 0;
    private static int totalTutorCount = 0;

    // Populate table using the shared database connection
    /*-----------------------------------Fatch database table------------------------------*/
    void displaydatabaseTable(String tableName, JTable table) {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + tableName + "`")) {

            // Create a DefaultTableModel
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            // Set column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Populate the JTable with data
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
            /*-----------------------------------User define Exception ------------------------------*/
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /*-----------------------------------Find total student or tutors or course------------------------------*/
    private void countRows(String tableName) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                /*-----------------------------------Update the tatal course or student or tutor ------------------------------*/
                switch (tableName) {
                    case "course":
                        totalCourseCount = rowCount;
                        break;
                    case "studentdetails":
                        totalStudentCount = rowCount;
                        break;
                    case "tutor":
                        totalTutorCount = rowCount;
                        break;
                }

                System.out.println("Successfully login'");
            } else {
                System.out.println("Fail login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getTotalCourseCount() {
        return totalCourseCount;
    }
    /*-----------------------------------Getter method to retrieve the total student ------------------------------*/
    public static int getTotalStudentCount() {
        return totalStudentCount;
    }
    /*-----------------------------------Getter method to retrieve the total Tutors ------------------------------*/
    public static int getTotalTutorCount() {
        return totalTutorCount;
    }

    public void updateUserInformation(String username, String userType) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addRow(new Object[]{username, userType});
    }



    /*----------------------------------------------------change password---------------------------*/

    /*-----------------------------------Main function  ------------------------------*/
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main_Bar");
        Main_Bar mainBar = new Main_Bar(frame);
        /*-----------------------------------Each table value pass ------------------------------*/
        mainBar.displaydatabaseTable("course", mainBar.table2);
        mainBar.displaydatabaseTable("tutor", mainBar.table3);
        mainBar.displaydatabaseTable("studentdetails", mainBar.table4);
        mainBar.displaydatabaseTable("modules", mainBar.table5);
        mainBar.displaydatabaseTable("enrol", mainBar.table6);
        mainBar.displaydatabaseTable("student", mainBar.table1);




        /*-----------------------------------Table name  ------------------------------*/
        mainBar.countRows("course");
        mainBar.countRows("tutor");
        mainBar.countRows("studentdetails");
        /*----------------------------------- Retrieve and print the total counts ------------------------------*/
        int totalCourseCount = mainBar.getTotalCourseCount();
        int totalTutorCount = mainBar.getTotalTutorCount();
        int totalStudentCount = mainBar.getTotalStudentCount();
        frame.setContentPane(mainBar.MainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
