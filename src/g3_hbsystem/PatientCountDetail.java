package g3_hbsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Abstract class for patient count details with polymorphism concepts
public abstract class PatientCountDetail {

    //Abstract method to be implemented by subclasses to count patients.
    public abstract int countPatients();

    //Method to execute a query and return the count.
    protected int executeCountQuery(String query) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error executing count query: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Return 0 if there's an error or no records
    }
}

// Subclass to count paid patients
class PaidPatients extends PatientCountDetail {
    @Override
    public int countPatients() {
        String query = "SELECT COUNT(*) FROM final_billing WHERE payment_status = true";
        return executeCountQuery(query);
    }
}

// Subclass to count unpaid patients
class UnpaidPatients extends PatientCountDetail {
    @Override
    public int countPatients() {
        String query = "SELECT COUNT(*) FROM final_billing WHERE payment_status = false";
        return executeCountQuery(query);
    }
}