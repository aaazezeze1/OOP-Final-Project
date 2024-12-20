package g3_hbsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hbsys_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "CY041205in";
    private static Connection connection = null;

    // Method to establish a connection to the database
    public static synchronized void startSession() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                //System.out.println("Database connection established successfully."); //check if database is working
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    // Method to close the connection to the database
    public static synchronized void endSession() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
    }

    // Getter for the database connection object
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                startSession(); // Ensure a connection is established if not already open
            }
        } catch (SQLException e) {
            System.out.println("Error checking database connection status: " + e.getMessage());
        }
        return connection;
    }
}
