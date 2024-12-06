/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package g3_hbsystem;

/**
 *
 * @author Amazing
 */


import java.util.Scanner;


//class person - inherited by the patient class
abstract class Person{

    private int patientId;
    private String patientName;

    public Person(int patientId, String patientName)
    {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public int getPatientId()
    {
        return patientId;
    }

    public void setPatientId()
    {
        this.patientId = patientId;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName()
    {
        this.patientName = patientName;
    }

    @Override
    public String toString()
    {
        return "Patient ID: " + patientId + ", Patient Name: " + patientName;
    }
}

//class InsuredPatient extends Patient {
//    public InsuredPatient(String name, int age, String roomType, double totalBill) {
//        super(name, age, roomType, totalBill);
//    }
//
//    public void UpdateBill(double newBill)
//    {
//        this.totalBill = newBill;
//    }
//}

// Custom class for program
public class G3_HBSystem {

//    static void SystemStart()
//    {
//        Scanner scanner = new Scanner(System.in);
//        try {
//            String ansYes = "Yes";
//            double bill = 0.00;
//
//            Patient patientClass;
//
//            System.out.println("------Patient Information------");
//            System.out.println("Patient Name: ");
//            String patientName = scanner.nextLine().toUpperCase();
//
//            System.out.println("Patient Age: ");
//            int patientAge = Integer.parseInt(scanner.nextLine());
//
//            System.out.println("Patient Room Type (Private, Ward, ICU, Out Patient): ");
//            String patientRoomType = scanner.nextLine();
//
//            patientRoomType = patientRoomType.toUpperCase();
//
//            if(patientRoomType.equals("PRIVATE")){
//                bill += 4000.00;
//            } if (patientRoomType.equals("WARD")) {
//                bill += 400.00;
//            } if (patientRoomType.equals("ICU")) {
//                bill += 6000.00;
//            } else {
//                bill += 0.00;
//            }
//
//            System.out.println("Patient got Doctor's Consultation? (Y/N): ");
//            String consult = scanner.nextLine().toUpperCase();
//
//            if (consult.equals("Y") || consult.equals(ansYes) || consult.equals(ansYes.toLowerCase())) {
//                bill += 1000;
//            }
//
//            System.out.println("Patient got Diagnostic Test? (Y/N): ");
//            String diagnostic = scanner.nextLine().toUpperCase();
//
//            if (diagnostic.equals("Y") || diagnostic.equals(ansYes) || diagnostic.equals(ansYes.toLowerCase())) {
//                bill += 1000;
//            }
//
//            // Ask for medicine then add to bill
//            System.out.println("Was the Patient Prescribed Medicine? (Y/N): ");
//            String prescribed = scanner.nextLine().toUpperCase();
//
//            if(prescribed.equals("Y") || prescribed.equals(ansYes) || prescribed.equals(ansYes.toLowerCase()))
//            {
//                System.out.println("List of Medicines: ");
//                System.out.println("1 - Analgesics\n"
//                        + "2 - Antacid\n"
//                        + "3 - Anti-Anxiety\n"
//                        + "4 - Antibacterial\n"
//                        + "5 - Antibiotics\n"
//                        + "6 - Anti-Depressant\n"
//                        + "7 - Anti-Diarrheal\n"
//                        + "8 - Antihistamine\n"
//                        + "9 - Anti-Fungal\n"
//                        + "10 - Anti-Pyrectics\n"
//                        + "11 - Anti-Viral\n"
//                        + "12 - Bronchodilators\n"
//                        + "13 - Decongestants\n"
//                        + "14 - Immunosuppressives\n"
//                        + "15 - Hypoglycemics\n"
//                );
//
//                System.out.println("Please enter the number corresponding to the medicine (ex. 1): ");
//                int medicine = Integer.parseInt(scanner.nextLine());
//
//                switch(medicine) {
//                    case 1: bill += 500; break;
//                    case 2: bill += 500; break;
//                    case 3: bill += 500; break;
//                    case 4: bill += 500; break;
//                    case 5: bill += 500; break;
//                    case 6: bill += 500; break;
//                    case 7: bill += 500; break;
//                    case 8: bill += 500; break;
//                    case 9: bill += 500; break;
//                    case 10: bill += 500; break;
//                    case 11: bill += 500; break;
//                    case 12: bill += 500; break;
//                    case 13: bill += 500; break;
//                    case 14: bill += 500; break;
//                    case 15: bill += 500; break;
//                }
//            }
//
//            System.out.println("Does Patient have Insurance? (Y/N): ");
//            String insured = scanner.nextLine().toUpperCase();
//
//            System.out.println("Is Patient a Person with Disability? (Y/N): ");
//            String isPWD = scanner.nextLine().toUpperCase();
//
//            if (isPWD.equals("Y") || isPWD.equals(ansYes) || isPWD.equals(ansYes.toLowerCase()))
//            {
//                double discount = 0.2;
//                bill -= bill * discount;
//            }
//            if (insured.equals("Y") || insured.equals(ansYes) || insured.equals(ansYes.toLowerCase())){
//                double insuranceDiscount = 0.4;
//                bill -= bill * insuranceDiscount;
//                patientClass = new InsuredPatient(patientName, patientAge, patientRoomType, bill);
//                ((InsuredPatient) patientClass).UpdateBill(bill);
//            }
//            else
//            {
//                patientClass = new Patient(patientName, patientAge, patientRoomType, bill);
//                ((Patient) patientClass).UpdateBill(bill);
//            }
//            // for display
//            ((Patient) patientClass).DisplayInfo();
//        }
//        catch (Exception e) {
//            System.out.println("Please Run the Program and Try Again.");
//        }
//    }

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