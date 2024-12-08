package g3_hbsystem;

import java.util.Scanner;

public class Admin {

    public void Login(Scanner scanner) {
        System.out.println("----- ADMIN LOGIN -----");
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
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("----- ADMIN MENU -----");
            System.out.println(" 1. Create New Patient Bill Record.");
            System.out.println(" 2. Payment Section.");
            System.out.println(" 3. View Patient Bill Record.");
            System.out.println(" 4. Exit.");
            System.out.println();

            System.out.print("Enter your choice here: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> G3_HBSystem.createPatientBill(scanner);
                case 2 -> G3_HBSystem.processPayment(scanner);
                case 3 -> G3_HBSystem.viewPatientRecord(scanner);
                case 4 -> {
                    System.out.println("Thank you for using G3 Hospital Billing System.");
                    loggedIn = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
