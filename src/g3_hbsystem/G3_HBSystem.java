/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

//HAVE PROBLEM WITH CALCULATION
package g3_hbsystem;

/*

  @author Amazing
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


//class person - inherited by the patient class
class Person{

    private final int patientId;
    private final String patientName;

    public Person(int patientId, String patientName)
    {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public int getPatientId()
    {
        return patientId;
    }

    //used in payment summary
    @Override
    public String toString()
    {
        return "Patient ID: " + patientId +
                "\nPatient Name: " + patientName;
    }
}

// Custom class for program
public class G3_HBSystem {

    private static int patientCounter = 1;

    //stores patient records
    private static final Map<Integer, Patient> patientRecords = new HashMap<>();

    //METHOD FOR CREATING PATIENT BILL
    public static void createPatientBill(Scanner scanner) {
        System.out.println("Patient ID:" + patientCounter);
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

        //create new patient obj
        Patient patient = new Patient(patientCounter++, name, age, address, phone, email);


        // Room Fee
        System.out.println("Did the patient stay in a room? (Yes/No)");
        String roomStay = scanner.nextLine();

        if (roomStay.equalsIgnoreCase("Yes")) {
            System.out.println("Enter Room Type (Private/Semi-Private/Ward/ICU):");
            String roomType = scanner.nextLine();
            System.out.println("Enter Duration (days):");
            int days = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline character

            double roomRate;

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

            // Call addBillItem method for room charges
            patient.addBillItem(roomType + " Room (" + days + " days)", roomRate * days);
        }

        // Consultation Fee (always included)
        System.out.println("\nDid the patient consult a doctor? (Yes/No)");
        if (scanner.nextLine().equalsIgnoreCase("Yes")) {
            double consultFee = 500.00;
            // Call addBillItem method for consultation fee
            patient.addBillItem("Consultation Fee", consultFee);
        }


        //scanner.nextLine();

        // For Diagnostic Procedures
        System.out.println("\nDid the patient undergo diagnostic procedures? (Yes/No)");
        if (scanner.nextLine().equalsIgnoreCase("Yes")) {
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
            scanner.nextLine(); // consume the newline

            double dprocedurePrice = 0;
            String procedureName = ""; // Add this to store the procedure name

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
                // Add the diagnostic procedure to the bill
                patient.addBillItem("Diagnostic Procedure - " + procedureName, dprocedurePrice);
            }
        }

        // For Therapeutic Procedures
        System.out.println("\nDid the patient undergo therapeutic procedures? (Yes/No)");
        if (scanner.nextLine().equalsIgnoreCase("Yes")) {
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

            double tprocedurePrice = 0;
            String tProcedureName = "";

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
                // Add the therapeutic procedure to the bill
                patient.addBillItem("Therapeutic Procedure - " + tProcedureName, tprocedurePrice);
            }
        }

        //for medication
        // List of medicines
        String[] medicines = {
                "Analgesics", "Antacid", "Anti-Anxiety", "Antibacterial", "Antibiotics",
                "Anti-Depressant", "Anti-Diarrheal", "Antihistamine", "Anti-Fungal",
                "Anti-Pyrectics", "Anti-Viral", "Bronchodilators", "Decongestants",
                "Immunosuppressives", "Hypoglycemics"
        };

        // Ask if the patient was prescribed medicine
        System.out.println("\nWas the patient prescribed medicine? (Yes/No): ");
        String prescribed = scanner.nextLine().toLowerCase();

        if (prescribed.equals("yes")) {
            // Ask how many medicines were prescribed
            System.out.println("How many medicines were prescribed? ");
            int numMedicines = Integer.parseInt(scanner.nextLine());

            // Print the list of available medicines
            System.out.println("List of available medicines:");
            for (int i = 0; i < medicines.length; i++) {
                System.out.println(medicines[i]);
            }

            // Loop to ask for the medicine name and quantity
            for (int i = 0; i < numMedicines; i++) {
                System.out.println("Enter the name of the medicine (e.g., Analgesics): ");
                String medicineName = scanner.nextLine().trim();

                // Check if the entered medicine is valid
                int medicineIndex = -1;
                for (int j = 0; j < medicines.length; j++) {
                    if (medicines[j].equalsIgnoreCase(medicineName)) {
                        medicineIndex = j;
                        break;
                    }
                }

                // If the medicine is valid, proceed; otherwise, prompt the user again
                if (medicineIndex != -1) {
                    // Ask for the quantity of the medicine
                    System.out.println("Enter the quantity for " + medicineName + ": ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    double totalPrice = 0;

                    switch (medicineIndex) {
                        case 0: totalPrice = 500.00 * quantity; break;
                        case 1: totalPrice = 150.00 * quantity; break;
                        case 2: totalPrice = 350.00 * quantity; break;
                        case 3: totalPrice = 400.00 * quantity; break;
                        case 4: totalPrice = 550.00 * quantity; break;
                        case 5: totalPrice = 600.00 * quantity; break;
                        case 6: totalPrice = 200.00 * quantity; break;
                        case 7: totalPrice = 250.00 * quantity; break;
                        case 8: totalPrice = 450.00 * quantity; break;
                        case 9: totalPrice = 300.00 * quantity; break;
                        case 10: totalPrice = 650.00 * quantity; break;
                        case 11: totalPrice = 450.00 * quantity; break; // Bronchodilators
                        case 12: totalPrice = 180.00 * quantity; break;
                        case 13: totalPrice = 700.00 * quantity; break; // Immunosuppressives
                        case 14: totalPrice = 400.00 * quantity; break; // Hypoglycemics
                        default: System.out.println("Invalid medicine!"); break;
                    }

                    // Add the medicine to the patient's bill
                    patient.addBillItem(quantity + " x " + medicineName, totalPrice);
                } else {
                    System.out.println("Invalid medicine name! Please enter a valid medicine from the list.");
                    i--; // Decrease the counter to retry the current iteration
                }
            }
        }

        System.out.println("\nDoes the patient have insurance? (Yes/No)");
        boolean hasInsurance = scanner.nextLine().equalsIgnoreCase("Yes");

        System.out.println("\nIs the patient a PWD? (Yes/No)");
        boolean isPWD = scanner.nextLine().equalsIgnoreCase("Yes");

        patient.applyDiscounts(hasInsurance, isPWD);

        //Save patient details to records
        patientRecords.put(patient.getPatientId(), patient);
        System.out.println("\nPatient bill created successfully!");
        System.out.println(patient);

        // Automatically return to admin menu after creation
        Admin.adminMenu(scanner);
    }

    // METHOD FOR PAYING
    public static void processPayment(Scanner scanner) {
        // Prompt the user to enter the Patient ID
        System.out.print("\nEnter Patient ID:");
        int patientId = scanner.nextInt(); // Read the Patient ID as an integer
        scanner.nextLine(); // Consume the newline character

        // get patient object from the patient records using the entered ID
        Patient patient;
        patient = patientRecords.get(patientId);

        // Check if the patient exists in the records
        if (patient == null) {
            // If no patient is found with the given ID, display an error message and exit
            System.out.println("\nPatient not found!");
            return;
        }

        // Display the patient's details (uses the overridden toString() method of Patient class)
        System.out.println(patient);

        // asks the user to enter the payment amount
        System.out.print("\nEnter payment amount:");
        double payment = scanner.nextDouble();
        scanner.nextLine();

        // Call the processPayment method
        patient.processPayment(payment);
    }


    // METHOD FOR VIEWING PATIENT BILL RECORD
    public static void viewPatientRecord(Scanner scanner) {
        // ask the user to enter the Patient ID
        System.out.print("\nEnter Patient ID:");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        // Retrieve the patient object from the patientRecords map using the entered ID
        Patient patient = patientRecords.get(patientId);

        // Check if the patient exists in the records
        if (patient == null) {// If no patient is found with the given ID, display an error message
            System.out.println("Patient not found!");
        }
        else
        {
            // If the patient is found, display their details
            // uses the overridden toString() method of the Patient class
            System.out.println(patient);
        }
    }

    public static void main(String[] args) {
        //connection for database

        //-------------------------------------------------------------------------------------------------------//

        Scanner scanner = new Scanner(System.in);

        Admin objAdmin = new Admin(); //object for admin class

        System.out.println(">>>============================================<<<");
        System.out.println("     WELCOME TO G3 HOSPITAL BILLING SYSTEM");
        System.out.println(">>>============================================<<<");
        System.out.println();

        boolean askingUser = true;
        //options asking if the user is an admin or want to exit
        while(askingUser)
        {
            System.out.println("Are you a...");
            System.out.println("Enter 'A' if Admin"); //create patients bills
            System.out.println("Enter 'E' to Exit"); //exit program
            System.out.println();

            System.out.println("Enter your choice: ");
            String userChoice = scanner.nextLine().trim().toUpperCase();

            switch (userChoice)
            {
                case "A":
                    objAdmin.Login(scanner);
                    askingUser = false;
                    break;
                case "E":
                    System.out.println("Thank you for using G3 Hospital Billing System!");
                    askingUser = false;
                    scanner.close();
                    break;
                default: System.out.println("Invalid input. Please type 'A' for Admin, 'P' for Patient, and 'E' to exit.");
            }

        }

    }
}