import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class TotalCST {

    private static int totalCourseCount = 0;
    private static int totalStudentCount = 0;
    private static int totalTutorCount = 0;

    public static void displaytotalCST(String tableName) {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost/dashboard?serverTimezone=UTC";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT COUNT(*) FROM " + tableName;

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    int rowCount = resultSet.getInt(1);

                    // Update the total counts based on the table name
                    if ("course".equals(tableName)) {
                        totalCourseCount = rowCount;
                    } else if ("students".equals(tableName)) {
                        totalStudentCount = rowCount;
                    } else if ("tutors".equals(tableName)) {
                        totalTutorCount = rowCount;
                    }

                    System.out.println("Total rows in the '" + tableName + "' table: " + rowCount);
                } else {
                    System.out.println("Failed to retrieve row count for " + tableName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter method to retrieve the total course count
    public static int getTotalCourseCount() {
        return totalCourseCount;
    }

    // Getter method to retrieve the total student count
    public static int getTotalStudentCount() {
        return totalStudentCount;
    }

    // Getter method to retrieve the total tutor count
    public static int getTotalTutorCount() {
        return totalTutorCount;
    }
}
