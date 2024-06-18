import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Result {
    private JTextField SearchtxtField;
    private JTable table1;
    private JLabel Updatename;
    private JLabel UpdateCourse;
    JPanel ResultPanel;
    private JButton deleteResultButton;
    private JLabel Result;
    private static final String URL = "jdbc:mysql://localhost/cms2331506?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Result() {
        SearchtxtField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentID = SearchtxtField.getText();

                try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String query = "SELECT ID, Name, Course, Module, Percentage, Grade " +
                            "FROM studentresult " +
                            "WHERE ID = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, studentID);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        DefaultTableModel tableModel = buildTableModel(resultSet);
                        table1.setModel(tableModel);

                        if (tableModel.getRowCount() > 0) {
                            // Student found, update labels
                            String studentName = tableModel.getValueAt(0, 1).toString();
                            Updatename.setText(studentName);

                            String course = tableModel.getValueAt(0, 2).toString();
                            UpdateCourse.setText(course);

                            // Check percentage and update Result label for pass/fail
                            double percentage = Double.parseDouble(tableModel.getValueAt(0, 4).toString());
                            if (percentage >= 40.0) {
                                Result.setText("Result: Pass");
                            } else {
                                Result.setText("Result: Fail");
                            }
                        } else {
                            // Student not found, clear labels
                            Updatename.setText("Not Found");
                            UpdateCourse.setText("Not Found");
                            Result.setText(""); // Clear result label if not found
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException appropriately
                }
            }
        });
        deleteResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteResultWindow();
            }
        });
    }
    private void openDeleteResultWindow() {
        JFrame addCourseFrame = new JFrame("Delete Result");
        addCourseFrame.setContentPane(new DeleteResult().DeleteandUpdatePanel);
        addCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCourseFrame.pack();
        addCourseFrame.setVisible(true);
    }

    private DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();
        String[] columnNames = {"ID", "Name", "Course", "Module", "Percentage", "Gread"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Result().resultshow();
            }
        });
    }

    private void resultshow() {
        JFrame frame = new JFrame("Result");
        frame.setContentPane(new Result().ResultPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
