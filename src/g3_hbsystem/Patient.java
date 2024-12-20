package g3_hbsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static g3_hbsystem.G3_HBSystem.*;

//Interface Concept
interface IPatient {

    // Save patient details to the database
    void saveToDatabase();
    // Save room fee details to the database
    void saveRoomFeeToDB();
    // Save consultation fee details to the database
    void saveConsulationToDB();
    // Save diagnostic procedure details to the database
    void saveDiagnosticToDB();
    // Save therapeutic procedure details to the database
    void saveTherapeuticToDB();
    // Save medication details to the database
    void saveMedicationToDB();
    // Save discount details to the database
    void saveDiscountsToDB();
    // Calculate the total discount
    void calculateTotalDiscount();
    // Calculate the total bill
    void calculateTotalBill(int patientId);
    // Calculate the final bill
    void calculateFinalBill(int patientId);
}

public class Patient extends Person implements IPatient {

    private final int age; // Patient's age
    private final String patientAddress; // Patient's address
    private final String patientPhoneNum; // Patient's phone number
    private final String email; // Patient's email address


    // Constructor to initialize patient details
    public Patient(int patientId, String patientName, int age, String patientAddress, String patientPhoneNum, String email) {
        super(patientId, patientName); // Call the superclass constructor for basic details
        this.age = age;
        this.patientAddress = patientAddress;
        this.patientPhoneNum = patientPhoneNum;
        this.email = email;
    }

    // Method to save patient details in the patient table
    @Override
    public void saveToDatabase() {
        String sql = "INSERT INTO patient (patient_id, patient_name, patient_age, patient_address, patient_phonenum, patient_email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setString(2, getPatientName());
            pstmt.setInt(3, age);
            pstmt.setString(4, patientAddress);
            pstmt.setString(5, patientPhoneNum);
            pstmt.setString(6, email);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient details saved successfully to the database.");
            } else {
                System.out.println("Error saving patient details to the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save room fee details in the roomfee table
    @Override
    public void saveRoomFeeToDB() {
        String sql2 = "INSERT INTO roomfee (patient_id, room_type, stay_duration, roomfee_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setString(2, roomType );
            pstmt.setInt(3, days );
            pstmt.setDouble(4, roomRate * days);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room fee details saved successfully to the database.");
            } else {
                System.out.println("Error saving room fee details to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save consultation fee details in the consultationfee table
    @Override
    public void saveConsulationToDB() {
        String sql3 = "INSERT INTO consultationfee (patient_id, consultation_price) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql3)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setDouble(2, consultFee);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Consultation fee details saved successfully to the database.");
            } else {
                System.out.println("Error saving consultation fee details to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save diagnostic details in the diagnosticprocedure table
    public void saveDiagnosticToDB() {
        String sql4 = "INSERT INTO diagnosticprocedure (patient_id, diagnostic_name, diagnostic_fee) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql4)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setString(2, procedureName );
            pstmt.setDouble(3, dprocedurePrice);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Diagnostic Procedure fee details saved successfully to the database.");
            } else {
                System.out.println("Error saving diagnostic procedure details to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save therapeutic details in the therapeuticprocedure table
    @Override
    public void saveTherapeuticToDB() {
        String sql4 = "INSERT INTO therapeuticprocedure (patient_id, therapy_name, therapy_fee) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql4)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setString(2, tProcedureName );
            pstmt.setDouble(3, tprocedurePrice );

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Therapeutic procedure fee details saved successfully to the database.");
            } else {
                System.out.println("Error saving therapeutic procedure details to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save medication details in the medicationfee table
    @Override
    public void saveMedicationToDB() {
        String sql5 = "INSERT INTO medicationfee (patient_id, medicine_name, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql5)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setString(2, medicineName);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, totalPrice * quantity);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medication fee details saved successfully to the database.");
            } else {
                System.out.println("Error saving medication fee details to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save discount details in the discounts table
    @Override
    public void saveDiscountsToDB() {
        String sql6 = "INSERT INTO discounts (patient_id, insurance, pwd, senior_citizen) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql6)) {

            pstmt.setInt(1, getPatientId());
            pstmt.setBigDecimal(2, insuranceDiscount);
            pstmt.setBigDecimal(3, pwdDiscount);
            pstmt.setBigDecimal(4, seniorCitizenDiscount);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Discount details saved successfully to the database.");
            } else {
                System.out.println("Error saving room fee details to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate total discount details in the discounts table
    @Override
    public void calculateTotalDiscount() {
        String totaldiscount_sql = "UPDATE discounts "
                + "SET total_discount = insurance + pwd + senior_citizen "
                + "WHERE patient_id = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(totaldiscount_sql)) {

            // Set the parameter for patient_id
            pstmt.setInt(1, getPatientId());

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Total discount calculated successfully for patient ID: " + getPatientId());
            } else {
                System.out.println("No rows were updated. Please check the patient ID: " + getPatientId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate total bill details in the billing table
    @Override
    public void calculateTotalBill(int patientId) {
        // SQL to fetch the necessary IDs based on patient_id
        String selectRoomFeeIdSql = "SELECT room_id FROM roomfee WHERE patient_id = ? LIMIT 1";
        String selectConsultationIdSql = "SELECT consultation_id FROM consultationfee WHERE patient_id = ? LIMIT 1";
        String selectDiagnosticIdSql = "SELECT diagnostic_id FROM diagnosticprocedure WHERE patient_id = ? LIMIT 1";
        String selectTherapeuticIdSql = "SELECT therapeutic_id FROM therapeuticprocedure WHERE patient_id = ? LIMIT 1";
        String selectMedicationIdSql = "SELECT medication_id FROM medicationfee WHERE patient_id = ? LIMIT 1";

        // SQL to insert into billing table
        String insertSql = "INSERT INTO billing (patient_id, roomfee_id, consultation_id, diagnostic_id, therapeutic_id, medication_id, total_bill) "
                + "VALUES (?, ?, ?, ?, ?, ?, 0.00);"; // Insert data with actual foreign key references

        // SQL to update the total bill for the patient
        String updateSql = "UPDATE billing "
                + "JOIN ( "
                + "    SELECT billing.patient_id, "
                + "           SUM(IFNULL(roomfee.roomfee_price, 0)) + "
                + "           SUM(IFNULL(consultationfee.consultation_price, 0)) + "
                + "           SUM(IFNULL(diagnosticprocedure.diagnostic_fee, 0)) + "
                + "           SUM(IFNULL(therapeuticprocedure.therapy_fee, 0)) + "
                + "           SUM(IFNULL(medicationfee.total_price, 0)) AS total_bill "
                + "    FROM billing "
                + "    LEFT JOIN roomfee ON billing.roomfee_id = roomfee.room_id "
                + "    LEFT JOIN consultationfee ON billing.consultation_id = consultationfee.consultation_id "
                + "    LEFT JOIN diagnosticprocedure ON billing.diagnostic_id = diagnosticprocedure.diagnostic_id "
                + "    LEFT JOIN therapeuticprocedure ON billing.therapeutic_id = therapeuticprocedure.therapeutic_id "
                + "    LEFT JOIN medicationfee ON billing.medication_id = medicationfee.medication_id "
                + "    WHERE billing.patient_id = ? "
                + "    GROUP BY billing.patient_id "
                + ") AS TotalBill ON billing.patient_id = TotalBill.patient_id "
                + "SET billing.total_bill = TotalBill.total_bill "
                + "WHERE billing.patient_id = ?;"; // Update query with patient_id parameter

        try (Connection conn = DatabaseConnection.getConnection()) {
            int roomFeeId = 0, consultationId = 0, diagnosticId = 0, therapeuticId = 0, medicationId = 0;

            // Fetch roomfee_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectRoomFeeIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    roomFeeId = rs.getInt("room_id");
                }
            }

            // Fetch consultation_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectConsultationIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    consultationId = rs.getInt("consultation_id");
                }
            }

            // Fetch diagnostic_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectDiagnosticIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    diagnosticId = rs.getInt("diagnostic_id");
                }
            }

            // Fetch therapeutic_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectTherapeuticIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    therapeuticId = rs.getInt("therapeutic_id");
                }
            }

            // Fetch medication_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectMedicationIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    medicationId = rs.getInt("medication_id");
                }
            }

            // Validate IDs before inserting into billing table
            if (roomFeeId == 0 || consultationId == 0 || diagnosticId == 0 || therapeuticId == 0 || medicationId == 0) {
                System.err.println("Error: Missing foreign key reference for patient_id " + patientId);
                return; // Exit the method if any ID is invalid
            }

            // Insert data into billing table
            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertSql)) {
                pstmtInsert.setInt(1, patientId);
                pstmtInsert.setInt(2, roomFeeId);
                pstmtInsert.setInt(3, consultationId);
                pstmtInsert.setInt(4, diagnosticId);
                pstmtInsert.setInt(5, therapeuticId);
                pstmtInsert.setInt(6, medicationId);
                pstmtInsert.executeUpdate();
                //System.out.println("Record inserted successfully.");
            }

            // Update total bill in billing table
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                pstmtUpdate.setInt(1, patientId);
                pstmtUpdate.setInt(2, patientId);
                pstmtUpdate.executeUpdate();
                //System.out.println("Total bill updated successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate final bill details in the final_billing table
    @Override
    public void calculateFinalBill(int patientId) {
        // SQL to fetch necessary IDs based on the patient_id
        String selectBillingIdSql = "SELECT billing_id FROM billing WHERE patient_id = ? LIMIT 1";
        String selectDiscountsIdSql = "SELECT discounts_id FROM discounts WHERE patient_id = ? LIMIT 1";
        String selectTotalBillSql = "SELECT total_bill FROM billing WHERE billing_id = ? LIMIT 1";
        String selectTotalDiscountsSql = "SELECT total_discount FROM discounts WHERE discounts_id = ? LIMIT 1";

        // SQL to insert into final_billing table
        String insertFinalBillSql = "INSERT INTO final_billing (patient_id, billing_id, discounts_id, final_bill, payment_status) "
                + "VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection()) {
            int billingId = 0;
            int discountsId = 0;
            double totalBill = 0.0;
            double totalDiscounts = 0.0;

            // Fetch billing_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectBillingIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    billingId = rs.getInt("billing_id");
                }
            }

            // Fetch discounts_id
            try (PreparedStatement pstmt = conn.prepareStatement(selectDiscountsIdSql)) {
                pstmt.setInt(1, patientId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    discountsId = rs.getInt("discounts_id");
                }
            }

            // Fetch total_bill
            try (PreparedStatement pstmt = conn.prepareStatement(selectTotalBillSql)) {
                pstmt.setInt(1, billingId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalBill = rs.getDouble("total_bill");
                }
            }

            // Fetch total_discount
            try (PreparedStatement pstmt = conn.prepareStatement(selectTotalDiscountsSql)) {
                pstmt.setInt(1, discountsId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalDiscounts = rs.getDouble("total_discount");
                }
            }

            // Validate IDs before inserting into final_billing
            if (billingId == 0 || discountsId == 0) {
                System.err.println("Error: Missing foreign key reference for patient_id " + patientId);
                return; // Exit the method if any ID is invalid
            }

            // Calculate final bill
            double finalBill = totalBill - (totalBill * totalDiscounts);
            String paymentStatus = (finalBill > 0) ? "Not Paid" : "Paid";

            // Insert into final_billing table
            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertFinalBillSql)) {
                pstmtInsert.setInt(1, patientId);
                pstmtInsert.setInt(2, billingId);
                pstmtInsert.setInt(3, discountsId);
                pstmtInsert.setDouble(4, finalBill);
                pstmtInsert.setInt(5, paymentStatus.equals("Not Paid") ? 0 : 1);
                pstmtInsert.executeUpdate();
                System.out.println("Final bill inserted successfully. Payment status: " + paymentStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}