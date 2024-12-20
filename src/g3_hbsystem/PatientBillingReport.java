package g3_hbsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Abstraction Concept
abstract class PatientBilling_Abstract {

    /**
     * Prints the details of a patient.
     * @param patientId the ID of the patient
     */
    public abstract void printPatientDetails(int patientId);

    /**
     * Prints the billing details of a patient.
     * @param patientId the ID of the patient
     */
    public abstract void printBillingDetails(int patientId);

    /**
     * Processes payment for a patient's bill.
     * @param patientId the ID of the patient
     * @param paymentAmount the payment amount
     */
    public abstract void processPaymentInDatabase(int patientId, double paymentAmount);

    /**
     * Checks if the patient's bill is fully paid.
     * @param patientId the ID of the patient
     * @return true if the bill is paid, false otherwise
     */
    public abstract boolean isBillPaid(int patientId);
}

public class PatientBillingReport extends PatientBilling_Abstract{

    @Override
    public void printPatientDetails(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Retrieve Patient Information in patient table
            String patientInfoQuery = "SELECT patient_id, patient_name, patient_age, patient_address, patient_phonenum, patient_email FROM patient WHERE patient_id = ?";
            PreparedStatement patientStmt = conn.prepareStatement(patientInfoQuery);
            patientStmt.setInt(1, patientId);

            // Make sure ResultSet is not closed prematurely
            ResultSet patientRS = patientStmt.executeQuery();
            if (patientRS.next()) {
                // Create an instance of Person class using the patient details
                Person patient = new Person(patientRS.getInt("patient_id"), patientRS.getString("patient_name"));

                // Print patient information
                System.out.println("\n===== Patient Information =====");
                System.out.println(patient.getPatientInformation()); // Using the getPatientInformation method in the Person class
                System.out.println("Age: " + patientRS.getInt("patient_age"));
                System.out.println("Address: " + patientRS.getString("patient_address"));
                System.out.println("Phone Number: " + patientRS.getString("patient_phonenum"));
                System.out.println("Email: " + patientRS.getString("patient_email"));
            } else {
                System.out.println("No patient found with ID: " + patientId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving patient details.");
        }
    }

    @Override
    public void printBillingDetails(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("\n===== Patient Bill Record =====");

            // Room Fee Details in roomfee table
            String roomFeeQuery = "SELECT room_type, stay_duration, roomfee_price FROM roomfee WHERE patient_id = ?";
            PreparedStatement roomStmt = conn.prepareStatement(roomFeeQuery);
            roomStmt.setInt(1, patientId);
            ResultSet roomRS = roomStmt.executeQuery();
            if (roomRS.next()) {
                System.out.println(roomRS.getString("room_type") + " Room (" +
                        roomRS.getInt("stay_duration") + " days): " +
                        roomRS.getDouble("roomfee_price"));
            } else {
                System.out.println("No room fee details found.");
            }

            // Consultation Fee in consultationfee table
            String consultFeeQuery = "SELECT consultation_price FROM consultationfee WHERE patient_id = ?";
            PreparedStatement consultStmt = conn.prepareStatement(consultFeeQuery);
            consultStmt.setInt(1, patientId);
            ResultSet consultRS = consultStmt.executeQuery();
            if (consultRS.next()) {
                System.out.println("Consultation: " + consultRS.getDouble("consultation_price"));
            } else {
                System.out.println("No consultation fee details found.");
            }

            // Diagnostic Fee Details in diagnosticprocedure table
            String diagnosticFeeQuery = "SELECT diagnostic_name, diagnostic_fee FROM diagnosticprocedure WHERE patient_id = ?";
            PreparedStatement diagStmt = conn.prepareStatement(diagnosticFeeQuery);
            diagStmt.setInt(1, patientId);
            ResultSet diagRS = diagStmt.executeQuery();
            if (diagRS.next()) {
                System.out.println("Diagnostic Procedure (" + diagRS.getString("diagnostic_name") + "): " +
                        diagRS.getDouble("diagnostic_fee"));
            } else {
                System.out.println("No diagnostic fee details found.");
            }

            // Therapeutic Fee Details in therapeuticprocedure table
            String therapeuticFeeQuery = "SELECT therapy_name, therapy_fee FROM therapeuticprocedure WHERE patient_id = ?";
            PreparedStatement therapyStmt = conn.prepareStatement(therapeuticFeeQuery);
            therapyStmt.setInt(1, patientId);
            ResultSet therapyRS = therapyStmt.executeQuery();
            if (therapyRS.next()) {
                System.out.println("Therapeutic Procedure (" + therapyRS.getString("therapy_name") + ": " +
                        therapyRS.getDouble("therapy_fee"));
            } else {
                System.out.println("No therapeutic fee details found.");
            }

            // Medication Fee Details in medicationfee table
            String medicationFeeQuery = "SELECT medicine_name, quantity, total_price FROM medicationfee WHERE patient_id = ?";
            PreparedStatement medStmt = conn.prepareStatement(medicationFeeQuery);
            medStmt.setInt(1, patientId);
            ResultSet medRS = medStmt.executeQuery();
            if (medRS.next()) {
                System.out.println("Medication (" + medRS.getInt("quantity") + " x " +
                        medRS.getString("medicine_name") + "): " +
                        medRS.getDouble("total_price"));
            } else {
                System.out.println("No medication fee details found.");
            }

            // Total Bill Details in billing table
            String totalBillQuery = "SELECT total_bill FROM billing WHERE patient_id = ?";
            PreparedStatement totalBillStmt = conn.prepareStatement(totalBillQuery);
            totalBillStmt.setInt(1, patientId);
            ResultSet totalBillRS = totalBillStmt.executeQuery();
            if (totalBillRS.next()) {
                System.out.println("\nTotal Bill (without discounts): " + totalBillRS.getDouble("total_bill"));
            } else {
                System.out.println("No total bill details found.");
            }

            // Discount details in discounts table
            String discountsQuery = "SELECT insurance, pwd, senior_citizen FROM discounts WHERE patient_id = ?";
            PreparedStatement discountsStmt = conn.prepareStatement(discountsQuery);
            discountsStmt.setInt(1, patientId);
            ResultSet discountsRS = discountsStmt.executeQuery();
            if (discountsRS.next()) {
                if (discountsRS.getDouble("insurance") > 0) {
                    System.out.println("\tInsurance Discount: " + discountsRS.getDouble("insurance"));
                }
                if (discountsRS.getDouble("pwd") > 0) {
                    System.out.println("\tPWD Discount: " + discountsRS.getDouble("pwd"));
                }
                if (discountsRS.getDouble("senior_citizen") > 0) {
                    System.out.println("\tSenior Citizen Discount: " + discountsRS.getDouble("senior_citizen"));
                }
            } else {
                System.out.println("No discount details found.");
            }

            // Final Bill and Payment Status in final_billing table
            String paymentDetailsQuery = "SELECT final_bill, payment_status, amount_paid, remaining_balance FROM final_billing WHERE patient_id = ?";
            PreparedStatement paymentDetailsStmt = conn.prepareStatement(paymentDetailsQuery);
            paymentDetailsStmt.setInt(1, patientId);
            ResultSet paymentDetailsRS = paymentDetailsStmt.executeQuery();
            if (paymentDetailsRS.next()) {
                double finalBill = paymentDetailsRS.getDouble("final_bill");
                boolean paymentStatus = paymentDetailsRS.getBoolean("payment_status");
                double amountPaid = paymentDetailsRS.getDouble("amount_paid");
                double remainingBalance = paymentDetailsRS.getDouble("remaining_balance");

                System.out.println("Final Bill (with discounts applied): " + finalBill);
                System.out.println("Payment Status: " + (paymentStatus ? "Paid" : "Unpaid"));

                // Display remaining balance or change based on the payment
                if (paymentStatus) {
                    double change = amountPaid - finalBill;
                    if (change > 0) {
                        System.out.println("Change: " + change);
                    } else {
                        System.out.println("Remaining balance: " + Math.abs(change));  // Show any negative balance
                    }
                } else {
                    // For unpaid bills, show remaining balance
                    System.out.println("Remaining balance: " + remainingBalance);
                }
            } else {
                System.out.println("No final billing details found.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving billing details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void processPaymentInDatabase(int patientId, double paymentAmount) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Fetch patient bill details
            String billQuery = "SELECT final_bill, amount_paid, payment_status, remaining_balance FROM final_billing WHERE patient_id = ?";
            PreparedStatement billStmt = conn.prepareStatement(billQuery);
            billStmt.setInt(1, patientId);
            ResultSet billRS = billStmt.executeQuery();

            if (billRS.next()) {
                double finalBill = billRS.getDouble("final_bill");
                double amountPaid = billRS.getDouble("amount_paid");
                boolean isPaid = billRS.getBoolean("payment_status");
                double remainingBalance = billRS.getDouble("remaining_balance");

                // Update the total amount paid
                double newAmountPaid = amountPaid + paymentAmount;

                // Calculate remaining balance after payment
                remainingBalance = finalBill - newAmountPaid;

                // If the remaining balance is 0 or less, mark the bill as paid
                if (remainingBalance <= 0) {
                    // Full payment received, update payment status to "Paid"
                    updatePaymentStatus(conn, patientId, newAmountPaid, true, 0); // Full payment
                    System.out.println("Payment processed successfully. Change: " + Math.abs(remainingBalance));
                } else {
                    // Partial payment, update remaining balance
                    updatePaymentStatus(conn, patientId, newAmountPaid, false, remainingBalance);
                    System.out.println("Partial payment recorded. Remaining balance: " + remainingBalance);
                }
            } else {
                System.out.println("No billing record found for patient ID: " + patientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error processing payment.");
        }
    }

    @Override
    public boolean isBillPaid(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Retrieve remaining balance and payment status
            String query = "SELECT remaining_balance, payment_status FROM final_billing WHERE patient_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double remainingBalance = rs.getDouble("remaining_balance");
                boolean paymentStatus = rs.getBoolean("payment_status");

                // Treat the bill as unpaid if payment_status is false, regardless of remaining_balance
                return paymentStatus; // A true value indicates the bill is fully paid
            }
        } catch (SQLException e) {
            System.out.println("Error checking payment status: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Default to unpaid if no record is found or an error occurs
    }

    public void updatePaymentStatus(Connection conn, int patientId, double amountPaid, boolean isPaid, double remainingBalance) throws SQLException {
        String updateQuery = "UPDATE final_billing SET amount_paid = ?, payment_status = ?, remaining_balance = ? WHERE patient_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setDouble(1, amountPaid);  // Update the amount paid
            stmt.setBoolean(2, isPaid);  // Update payment status (true = paid, false = unpaid)
            stmt.setDouble(3, remainingBalance);  // Update the remaining balance
            stmt.setInt(4, patientId);  // Update for the given patient
            stmt.executeUpdate();
        }
    }
}
