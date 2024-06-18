import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertResult {
    JPanel ResultFormPanel;
    private JTextField SName;
    private JTextField MName;
    private JTextField Percentage;
    private JTextField Grade;
    private JButton btnUpdate;
    private JTextField SID;
    private JComboBox<String> SelectCourse;
    private JTextField ModuleName;

    public InsertResult() {
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
    }

    private void insertData() {
        String url = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO studentresult (ID, Name, Course, Module, Percentage, Grade) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, SID.getText());
                preparedStatement.setString(2, SName.getText());
                preparedStatement.setString(3, SelectCourse.getSelectedItem().toString());
                preparedStatement.setString(4, ModuleName.getText());
                preparedStatement.setString(5, Percentage.getText());
                preparedStatement.setString(6, Grade.getText());

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data inserted successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting data into the database.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InsertResult");
        frame.setContentPane(new InsertResult().ResultFormPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
