/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package g3_hbsystem;

/*

  @author Amazing
 */


import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.math.BigDecimal;

import static g3_hbsystem.DatabaseConnection.endSession;
import static g3_hbsystem.DatabaseConnection.startSession;


//class person - inherited by the patient class
class Person {
    private final int patientId;
    private final String patientName;

    public Person(int patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    // Method to directly return patient details (without recursion)
    public String getPatientInformation() {
        return "\nPatient ID: " + this.patientId + "\nPatient Name: " + this.patientName;
    }

    // Static method to fetch patient details from the database, returning a Person object
    public static Person fetchPatientInformation(int patientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT patient_id, patient_name FROM patient WHERE patient_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Person(rs.getInt("patient_id"), rs.getString("patient_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving patient information.");
        }
        return null; // Return null if no patient found
    }
}


// Custom class for program
public class G3_HBSystem {

    // Static variable to keep track of the next available patient ID
    private static int patientCounter = 1;

    // Static block to initialize the patient counter when the class is loaded
    static
    {
        initializePatientCounter();
    }

    // Method to initialize the patient counter by querying the database for the highest patient ID
    private static void initializePatientCounter() {
        Connection connection = DatabaseConnection.getConnection();     // Get a connection to the database
        try (Statement statement = connection.createStatement()) {
            // SQL query to find the maximum patient ID in the patient table
            String query = "SELECT MAX(patient_id) AS max_id FROM patient";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    // If records exist, set patientCounter to the next ID
                    patientCounter = resultSet.getInt("max_id") + 1;
                } else {
                    // If no records exist, initialize patientCounter to 1
                    patientCounter = 1;
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing patient counter: " + e.getMessage());
            patientCounter = 1; // Default in case of an error
        }
    }

    public static String roomType;
    public static int days;
    public static double roomRate;

    public static double consultFee;

    public static double dprocedurePrice;
    public static String procedureName;

    public static double tprocedurePrice;
    public static String tProcedureName;

    public static String medicineName;
    public static int quantity;
    public static double totalPrice;

    // Variables for discounts as BigDecimal
    public static BigDecimal insuranceDiscount = BigDecimal.ZERO;
    public static BigDecimal pwdDiscount = BigDecimal.ZERO;
    public static BigDecimal seniorCitizenDiscount = BigDecimal.ZERO;

    //METHOD FOR CREATING PATIENT BILL
    public static void createPatientBill(Scanner scanner) {

        try {
            // Refresh patientCounter from the database
            initializePatientCounter();

            System.out.print("\n-------- Enter Patient Details --------");

            System.out.println("\nPatient ID:" + patientCounter);
            scanner.nextLine();
            System.out.print("Enter Patient Name:");
            String name = scanner.nextLine();
            System.out.print("Enter Age:");
            int age = scanner.nextInt();

            scanner.nextLine();
            System.out.print("Enter Address:");
            String address = scanner.nextLine();

            System.out.print("Enter Phone Number:");
            String phone = scanner.nextLine();
            System.out.print("Enter Email:");
            String email = scanner.nextLine();

            System.out.print("");

            //create an obj for the interface to implement methods in patient class
            IPatient iPatientobj = new Patient(patientCounter++, name, age, address, phone, email);

            iPatientobj.saveToDatabase();

            //--------------------------------------------------------------------------------------------------------//

            // Room Fee
            System.out.println("\nDid the patient stay in a room? (Yes/No)");
            String roomStay = scanner.nextLine();

            if (roomStay.equalsIgnoreCase("Yes")) {
                System.out.println("Enter Room Type (Private/Semi-Private/Ward/ICU):");
                roomType = scanner.nextLine();
                System.out.println("Enter Duration (days):");
                days = scanner.nextInt();
                scanner.nextLine();

                switch (roomType.toLowerCase()) {
                    case "private":
                        roomRate = 4000;
                        break;
                    case "semi-private":
                        roomRate = 2000;
                        break;
                    case "ward":
                        roomRate = 1200;
                        break;
                    case "icu":
                        roomRate = 5000;
                        break;
                    default:
                        System.out.println("Invalid room type. Returning to room charge menu.");
                        return; // Ends this flow and allows retry or exit to main menu
                }
                // Saving input in the database
                iPatientobj.saveRoomFeeToDB();
            }
            else if(roomStay.equalsIgnoreCase("No"))
            {
                roomType = "None";
                days = 0;
                roomRate = 0;
                // Saving default in the database
                iPatientobj.saveRoomFeeToDB();
                System.out.println("No room fee to record. Proceed to Consultation Fee.");
            }

            //--------------------------------------------------------------------------------------------------------//

            // Consultation Fee (always included)
            System.out.println("\nDid the patient consult a doctor? (Yes/No)");
            String consulted = scanner.nextLine();
            if (consulted.equalsIgnoreCase("Yes")) {
                consultFee = 500;
                // Saving input in the database
                iPatientobj.saveConsulationToDB();
            } else if (consulted.equalsIgnoreCase("No")){
                consultFee = 0;
                // Saving default in the database
                iPatientobj.saveConsulationToDB();
                System.out.println("No consultation fee to record. Proceed to Diagnostic Procedure.");
            }

            //--------------------------------------------------------------------------------------------------------//

            // Diagnostic Procedures
            System.out.println("\nDid the patient undergo diagnostic procedures? (Yes/No)");
            String diagnosed = scanner.nextLine();
            if (diagnosed.equalsIgnoreCase("Yes")) {
                System.out.println("List of Diagnostic Procedures: ");
                System.out.println("1 - Blood Test");
                System.out.println("2 - X-ray");
                System.out.println("3 - Ultrasound");
                System.out.println("4 - MRI (Magnetic Resonance Imaging)");
                System.out.println("5 - CT Scan (Computed Tomography)");
                System.out.println("6 - Electrocardiogram (ECG)");
                System.out.println("7 - Urinalysis");
                System.out.println("8 - Chest X-ray");
                System.out.println("9 - Mammography");

                System.out.println("Enter the number corresponding to the diagnostic procedure (e.g., 1): ");
                int dprocedure = scanner.nextInt();
                scanner.nextLine();

                dprocedurePrice = 0;
                procedureName = "";

                switch (dprocedure) {
                    case 1: dprocedurePrice = 500.00; procedureName = "Blood Test"; break;
                    case 2: dprocedurePrice = 300.00; procedureName = "X-ray"; break;
                    case 3: dprocedurePrice = 150.00; procedureName = "Ultrasound"; break;
                    case 4: dprocedurePrice = 1000.00; procedureName = "MRI"; break;
                    case 5: dprocedurePrice = 1200.00; procedureName = "CT Scan"; break;
                    case 6: dprocedurePrice = 250.00; procedureName = "Electrocardiogram (ECG)"; break;
                    case 7: dprocedurePrice = 100.00; procedureName = "Urinalysis"; break;
                    case 8: dprocedurePrice = 350.00; procedureName = "Chest X-ray"; break;
                    case 9: dprocedurePrice = 200.00; procedureName = "Mammography"; break;
                    default: System.out.println("Invalid choice! Please select a valid diagnostic procedure."); break;
                }

                if (!procedureName.isEmpty()) {
                    // Saving input in the database
                    iPatientobj.saveDiagnosticToDB();
                }
            } else if (diagnosed.equalsIgnoreCase("No"))
            {
                procedureName = "None";
                dprocedurePrice = 0;
                // Saving default in the database
                iPatientobj.saveDiagnosticToDB();
                System.out.println("No diagnostic procedures to record. Proceed to Therapeutic Procedure.");
            }

            //--------------------------------------------------------------------------------------------------------//

            // Therapeutic Procedures
            System.out.println("\nDid the patient undergo therapeutic procedures? (Yes/No)");
            String therapy = scanner.nextLine();
            if (therapy.equalsIgnoreCase("Yes")) {
                System.out.println("List of Therapeutic Procedures: ");
                System.out.println("1 - Physical Therapy");
                System.out.println("2 - Chemotherapy");
                System.out.println("3 - Dialysis");
                System.out.println("4 - Radiotherapy");
                System.out.println("5 - Surgery");
                System.out.println("6 - Electroconvulsive Therapy (ECT)");
                System.out.println("7 - Spinal Manipulation");
                System.out.println("8 - Laser Therapy");
                System.out.println("9 - Acupuncture");

                System.out.println("Enter the number corresponding to the therapeutic procedure (e.g., 1): ");
                int tprocedure = scanner.nextInt();
                scanner.nextLine();

                tprocedurePrice = 0;
                tProcedureName = "";

                switch (tprocedure) {
                    case 1: tprocedurePrice = 500.00; tProcedureName = "Physical Therapy"; break;
                    case 2: tprocedurePrice = 1500.00; tProcedureName = "Chemotherapy"; break;
                    case 3: tprocedurePrice = 2000.00; tProcedureName = "Dialysis"; break;
                    case 4: tprocedurePrice = 2500.00; tProcedureName = "Radiotherapy"; break;
                    case 5: tprocedurePrice = 5000.00; tProcedureName = "Surgery"; break;
                    case 6: tprocedurePrice = 1000.00; tProcedureName = "Electroconvulsive Therapy (ECT)"; break;
                    case 7: tprocedurePrice = 300.00; tProcedureName = "Spinal Manipulation"; break;
                    case 8: tprocedurePrice = 800.00; tProcedureName = "Laser Therapy"; break;
                    case 9: tprocedurePrice = 200.00; tProcedureName = "Acupuncture"; break;
                    default: System.out.println("Invalid choice! Please select a valid therapeutic procedure."); break;
                }

                if (!tProcedureName.isEmpty()) {
                    // Saving input in the database
                    iPatientobj.saveTherapeuticToDB();
                }
            }  else if (therapy.equalsIgnoreCase("No"))
            {
                tProcedureName = "None";
                tprocedurePrice = 0;
                // Saving default in the database
                iPatientobj.saveTherapeuticToDB();
                System.out.println("No therapeutic procedures to record. Proceed to Medication Fee.");
            }

//--------------------------------------------------------------------------------------------------------//

// Medications
            System.out.println("\nWas the patient prescribed medication? (Yes/No)");
            String prescribed = scanner.nextLine();
            if (prescribed.equalsIgnoreCase("Yes")) {
                System.out.println("List of Medications: ");
                System.out.println("1 - Analgesics");
                System.out.println("2 - Antacid");
                System.out.println("3 - Anti-Anxiety");
                System.out.println("4 - Antibacterial");
                System.out.println("5 - Antibiotics");
                System.out.println("6 - Anti-Depressant");
                System.out.println("7 - Anti-Diarrheal");
                System.out.println("8 - Antihistamine");
                System.out.println("9 - Anti-Fungal");
                System.out.println("10 - Anti-Pyrectics");
                System.out.println("11 - Anti-Viral");
                System.out.println("12 - Bronchodilators");
                System.out.println("13 - Decongestants");
                System.out.println("14 - Immunosuppressives");
                System.out.println("15 - Hypoglycemics");

                System.out.println("Enter the number corresponding to the prescribed medication (e.g., 1): ");
                int medicationChoice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                totalPrice = 0;
                medicineName = "";

                switch (medicationChoice) {
                    case 1: totalPrice = 500.00; medicineName = "Analgesics"; break;
                    case 2: totalPrice = 150.00; medicineName = "Antacid"; break;
                    case 3: totalPrice = 350.00; medicineName = "Anti-Anxiety"; break;
                    case 4: totalPrice = 400.00; medicineName = "Antibacterial"; break;
                    case 5: totalPrice = 550.00; medicineName = "Antibiotics"; break;
                    case 6: totalPrice = 600.00; medicineName = "Anti-Depressant"; break;
                    case 7: totalPrice = 200.00; medicineName = "Anti-Diarrheal"; break;
                    case 8: totalPrice = 250.00; medicineName = "Antihistamine"; break;
                    case 9: totalPrice = 450.00; medicineName = "Anti-Fungal"; break;
                    case 10: totalPrice = 300.00; medicineName = "Anti-Pyrectics"; break;
                    case 11: totalPrice = 650.00; medicineName = "Anti-Viral"; break;
                    case 12: totalPrice = 450.00; medicineName = "Bronchodilators"; break;
                    case 13: totalPrice = 180.00; medicineName = "Decongestants"; break;
                    case 14: totalPrice = 700.00; medicineName = "Immunosuppressives"; break;
                    case 15: totalPrice = 400.00; medicineName = "Hypoglycemics"; break;
                    default:
                        System.out.println("Invalid choice! Please select a valid medication.");
                        break;
                }

                if (!medicineName.isEmpty()) {
                    System.out.println("Enter the quantity for " + medicineName + ": ");
                    quantity = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer
                    // Saving input in the database
                    iPatientobj.saveMedicationToDB();
                }
            } else if(prescribed.equalsIgnoreCase("No"))
            {
                medicineName = "None";
                totalPrice = 0;
                quantity = 0;
                // Saving default in the database
                iPatientobj.saveMedicationToDB();
                System.out.println("No medication prescribed. Proceed to Discounts Management.");
            }

            //--------------------------------------------------------------------------------------------------------//

            //For discounts
            //insurance discounts
            System.out.println("\nDoes the patient have insurance? (Yes/No)");
            boolean hasInsurance = scanner.nextLine().equalsIgnoreCase("Yes");
            if (hasInsurance) {
                insuranceDiscount = BigDecimal.valueOf(0.20); // 20% insurance discount
            } else {
                insuranceDiscount = BigDecimal.ZERO; // No discount
            }
            //pwd discount
            System.out.println("\nIs the patient a PWD? (Yes/No)");
            boolean isPWD = scanner.nextLine().equalsIgnoreCase("Yes");
            if (isPWD) {
                pwdDiscount = BigDecimal.valueOf(0.15); // 15% PWD discount
            } else {
                pwdDiscount = BigDecimal.ZERO; // No discount
            }
            //senior citizen discount
            if (age >= 60) {
                seniorCitizenDiscount = BigDecimal.valueOf(0.10); // 10% senior citizen discount
            } else {
                seniorCitizenDiscount = BigDecimal.ZERO; // No discount
            }
            //saving input in the database
            iPatientobj.saveDiscountsToDB();

            //--------------------------------------------------------------------------------------------------------//
            int patientId = patientCounter - 1;

            //calculating the total bill without discounts
            iPatientobj.calculateTotalBill(patientId);
            //calculating the total discounts
            iPatientobj.calculateTotalDiscount();
            //calculating the final bill with total discounts
            iPatientobj.calculateFinalBill(patientId);

            System.out.println("\nPatient bill created successfully!");

            //obj of the abstract class which is extended by PatientBillingReport class
            PatientBilling_Abstract AbstractPatientBilling_obj = new PatientBillingReport();

            AbstractPatientBilling_obj.printPatientDetails(patientId);

            AbstractPatientBilling_obj.printBillingDetails(patientId);


        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please enter a valid number.");
            scanner.next(); // Clear the invalid input
            createPatientBill(scanner);

        } catch (Exception e) {
            System.out.println("An error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    // METHOD FOR PAYING
    public static void processPayment(Scanner scanner) {
        try {
            //PatientBillingReport recordReportPayment = new PatientBillingReport();
            PatientBilling_Abstract absrecordReportPayment_obj = new PatientBillingReport();

            System.out.println("\n----- Payment Section -----");
            // Prompt the user to enter the Patient ID
            System.out.print("\nEnter Patient ID: ");
            int patientId = scanner.nextInt(); // Read the Patient ID as an integer
            scanner.nextLine(); // Consume the newline character

            // Check if the patient exists in the database records
            Person patient = Person.fetchPatientInformation(patientId);
            if (patient != null) {
                System.out.println("Patient ID: " + patient.getPatientId() +
                        "\nPatient Name: " + patient.getPatientName());
                // Print the patient billing record using the printBillingDetails in the PatientBillingReport class
                absrecordReportPayment_obj.printBillingDetails(patientId);

                // Check if the bill is already paid or if the remaining balance is 0
                if (absrecordReportPayment_obj.isBillPaid(patientId)) {
                    System.out.println("\nThe bill for this patient has already been paid. No further payment is required.");
                    return; // Exit the method early if the bill is paid
                }

                // Ask the user to enter the payment amount if the bill is not paid
                System.out.print("\nEnter payment amount: ");
                double payment = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                // Call the processPaymentInDatabase method to edit the payment status in the database and the final bill
                absrecordReportPayment_obj.processPaymentInDatabase(patientId, payment);
            } else {
                System.out.println("No patient found with ID: " + patientId);
            }
            return; // Exit the method
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.nextLine(); // Clear the invalid input
            processPayment(scanner); // Re-run the method
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    // METHOD FOR VIEWING PATIENT BILL RECORD
    public static void viewPatientRecord(Scanner scanner) {
        while (true) { // Loop to allow re-entering if invalid input is provided
            try {
                System.out.println("\n----- View Patient Bill Record -----");
                // ask the user to enter the Patient ID
                System.out.print("\nEnter Patient ID: ");
                int patientId = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                // Check if the patient exists in the database records
                // Use the getPatientInformation method from the Person class to retrieve patient details
                Person patient = Person.fetchPatientInformation(patientId);
                if (patient != null) {
                    System.out.println("Patient ID: " + patient.getPatientId() +
                            "\nPatient Name: " + patient.getPatientName());

                    // print the patient billing record using the printBillingDetails in the PatientBillingReport class
                    PatientBillingReport recordReport = new PatientBillingReport();
                    recordReport.printBillingDetails(patientId);
                    break; // Exit the loop once a valid patient ID is entered
                } else {
                    System.out.println("No patient found with ID: " + patientId);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the Patient ID.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------------//
    //METHOD TO VIEW THE COUNT OF THE PAID OR UNPAID PATIENTS IN THE DATABASE -- part of polymorphism
    public static void viewCountofPatients(Scanner scanner) {
        System.out.println("\n----- Number of Paid and Unpaid Patients -----");
        System.out.println("Choose an option:");
        System.out.println("1. View number of paid patients");
        System.out.println("2. View number of unpaid patients");
        System.out.print("Enter your choice (1 or 2): ");

        while (true) { // Loop until a valid input is provided
            try {
                int choice = scanner.nextInt(); // Read user's choice
                PatientCountDetail countDetail;
                scanner.nextLine(); // Consume the newline character left by nextInt()
                switch (choice) {
                    case 1: // Paid Patients
                        countDetail = new PaidPatients();
                        System.out.println("Number of patients who have paid: " + countDetail.countPatients());
                        return; // Exit the loop
                    case 2: // Unpaid Patients
                        countDetail = new UnpaidPatients();
                        System.out.println("Number of patients with unpaid bills: " + countDetail.countPatients());
                        return; // Exit the loop
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice (1 or 2).");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(">>>============================================<<<");
        System.out.println("     WELCOME TO G3 HOSPITAL BILLING SYSTEM");
        System.out.println(">>>============================================<<<");
        System.out.println();

        System.out.println("------- ADMIN LOGIN -------");
        System.out.println();

        boolean notLoggedIn = true;

        String username = "Admin";
        String password = "Admin123";

        while (notLoggedIn) {
            try {
                System.out.print("Enter Username: ");
                String usernameInput = scanner.next();
                System.out.print("Enter Password: ");
                String passwordInput = scanner.next();

                if (usernameInput.equals(username) && passwordInput.equals(password)) {
                    System.out.println();
                    System.out.println("Login Success. Welcome Admin!");
                    System.out.println();
                    notLoggedIn = false;
                    adminMenu(scanner);
                } else {
                    System.out.println();
                    System.out.println("Wrong Username and Password. Please Try Again.");
                    System.out.println();
                }
            } catch (Exception e) {
                System.out.println("Please Try Again.");
            }
        }

    }

    public static void adminMenu(Scanner scanner) {
        // Connect to the database
        startSession();

        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--------------------- ADMIN MENU ---------------------");
            System.out.println(" 1. Create New Patient Bill Record.");
            System.out.println(" 2. Payment Section.");
            System.out.println(" 3. View Patient Bill Record.");
            System.out.println(" 4. View Count of Paid or Unpaid Patient Bill Record.");
            System.out.println(" 5. Exit.");
            System.out.println();

            System.out.print("Enter your choice here: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> G3_HBSystem.createPatientBill(scanner);
                    case 2 -> G3_HBSystem.processPayment(scanner);
                    case 3 -> G3_HBSystem.viewPatientRecord(scanner);
                    case 4 -> G3_HBSystem.viewCountofPatients(scanner);
                    case 5 -> {
                        System.out.println("Thank you for using G3 Hospital Billing System.");
                        loggedIn = false;
                        System.out.println("Logging out...");
                        endSession(); // End connection to the database
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                // Prompt the user to press enter to go to the admin menu
                if (loggedIn) {
                    System.out.println("\nPress 'enter' to go back to the admin menu.");
                    scanner.nextLine(); // Wait for the user to press 'enter'
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}