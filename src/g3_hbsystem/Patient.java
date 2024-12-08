package g3_hbsystem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Patient extends Person {

    // Map to store bill items and their respective costs
    private final Map<String, Double> billDetails = new LinkedHashMap<>();
    private final int age; // Patient's age
    private final String patientAddress; // Patient's address
    private final String patientPhoneNum; // Patient's phone number
    private final String email; // Patient's email address

    private double totalBill; // Total bill amount before discounts
    private boolean isPaid = false; // Payment status of the bill
    private double insuranceDiscount = 0; // Discount for insured patients
    private double pwdDiscount = 0; // Discount for PWD
    private double seniorCitizenDiscount = 0; // Discount for senior citizens

    // Constructor to initialize patient details
    public Patient(int patientId, String patientName, int age, String patientAddress, String patientPhoneNum, String email) {
        super(patientId, patientName); // Call the superclass constructor for basic details
        this.age = age;
        this.patientAddress = patientAddress;
        this.patientPhoneNum = patientPhoneNum;
        this.email = email;
    }

    // Method to add a bill item and update the total bill
    public void addBillItem(String description, double amount) {
        billDetails.put(description, amount); // Add the item to the bill details map
        totalBill += amount; // Add the item's cost to the total bill
    }

    // Method to apply discounts based on patient criteria and print the details
    public void applyDiscounts(boolean hasInsurance, boolean isPWD) {
        System.out.println("\n-------------------");
        System.out.println("Total Bill Without Discounts: " + totalBill);

        double discount = 0;

        // Apply 40% discount for insured patients
        if (hasInsurance) {
            insuranceDiscount = totalBill * 0.4;
            discount += 0.4;
            System.out.println("Insurance Coverage: " + insuranceDiscount);
        }

        // Apply 20% discount for PWD
        if (isPWD) {
            pwdDiscount = totalBill * 0.2;
            discount += 0.2;
            System.out.println("PWD Discount: " + pwdDiscount);
        }

        // Apply 10% discount for senior citizens (age 60 or older)
        if (age >= 60) {
            seniorCitizenDiscount = totalBill * 0.1;
            discount += 0.1;
            System.out.println("Senior Citizen Discount: " + seniorCitizenDiscount);
        }

        // Calculate the discount amount and final bill after applying discounts
        double discountAmount = insuranceDiscount + pwdDiscount + seniorCitizenDiscount;
        double finalBill = totalBill - discountAmount;

        System.out.println("\n-------------------");
        System.out.println("Total Bill With Discounts: " + finalBill);

        // Update the total bill with the final amount
        setTotalBill(finalBill);
    }

    // Private method to set the total bill (used internally after applying discounts)
    private void setTotalBill(double finalBill) {
        this.totalBill = finalBill;
    }

    // Method to process payment for the patient
    public void processPayment(double payment) {
        if (payment >= totalBill) { // If payment is greater than or equal to the total bill
            isPaid = true; // Mark the bill as fully paid
            System.out.println("Payment processed successfully. Change: " + (payment - totalBill));
        } else {
            // If payment is insufficient, display the remaining balance
            System.out.println("Remaining balance: " + (totalBill - payment));
        }
    }

    // Getter to check if the bill is fully paid
    public boolean isPaid() {
        return isPaid;
    }

    // Getter for the total bill amount
    public double getTotalBill() {
        return totalBill;
    }

    @Override
    public String toString() {
        // Build a summary string for the patient's bill
        String summary = "\n=====PATIENT BILL SUMMARY=====\n";

        // Add patient details
        summary += "\n----PATIENT INFO----\n";
        summary = summary + super.toString() + "\n"
                + "Age: " + age + "\n"
                + "Address: " + patientAddress + "\n"
                + "Phone Number: " + patientPhoneNum + "\n"
                + "Email: " + email + "\n";

        // Add all bill items and their costs
        summary += "\n-------------------\n";
        for (Map.Entry<String, Double> entry : billDetails.entrySet()) {
            summary += entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + "\n";
        }

        // Add the total bill amount before and after discounts
        summary += "\n-------------------\n";
        summary += "Total Bill Without Discounts: " + String.format("%.2f", totalBill + insuranceDiscount + pwdDiscount + seniorCitizenDiscount) + "\n";
        summary += "Insurance Discount: " + String.format("%.2f", insuranceDiscount) + "\n";
        summary += "PWD Discount: " + String.format("%.2f", pwdDiscount) + "\n";
        summary += "Senior Citizen Discount: " + String.format("%.2f", seniorCitizenDiscount) + "\n";
        summary += "\n-------------------\n";
        summary += "Final Bill (After Discounts): " + String.format("%.2f", totalBill) + "\n";

        // Add payment status
        if (isPaid) {
            summary += "\nStatus: Fully Paid\n";
        } else {
            summary += "\nStatus: Not Paid\n";
        }

        return summary; // Return the complete summary
    }
}
