package se.lexicon;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {
    static Scanner scanner = new Scanner(System.in);

    //This method validates numerical user inputs (Ex. selected options)
    public static int validateInt(int min, int max){
        int option;
        while (true){
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                // Check if the input is within the allowed range
                if (option < min || option > max){
                    throw new IllegalArgumentException();
                }
                break; // valid input, exit loop
            }catch (InputMismatchException e){
                System.out.print("Invalid Option! Please enter a valid number (" + min + "-" + max + "): ");
                scanner.nextLine();
            }
        }
        return option;
    }

    // This method validates string user inputs (Ex. name, full name)
    public static String validateName(String type) {
        String name;

        while (true) {
            System.out.print("Enter "+type+" Name: ");
            name = scanner.nextLine().trim();

            // Check if name is empty or contains invalid characters
            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                System.out.println("Invalid name! Only letters and spaces are allowed.");
            }else{
                break;
            }
        }
        return name;
    }

    //This method validates telephone number inputs.
    public static String validateTelephoneNumber(String type) {
        String phone;
        while (true) {
            System.out.print("Enter "+type+" Number: ");
            phone = scanner.nextLine().trim();

            // Check if phone is digits only and 7-15 characters long
            if (!phone.matches("\\d{7,15}")) {
                System.out.println("Invalid phone number! Enter 7-15 digits only. Try again.");
            }else{
                break;
            }
        }
        return phone;
    }

    // This method validates user's y/n inputs
    public static char validateYesNo(String message) {
        while (true) {
            IO.print(message);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y") || input.equals("N")) {
                return input.charAt(0);
            }

            IO.println("Invalid input!");
        }
    }

    // This method validates CSV file paths
    public static String validateFilePath(String type) {

        IO.print(String.format("""
                Example 1: data/%s/contacts.csv
                Example 2: C:/Users/JAYANI/Downloads/contacts.csv
                """, type));
        while (true) {
            IO.print("Enter file path (.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) { // Empty check file path
                IO.println("Path cannot be empty.");
                continue;
            }

            if (!path.toLowerCase().endsWith(".csv")) { // Ensure .csv extension
                IO.println("File must have .csv extension.");
                continue;
            }

            // Validate path format
            try {
                Path filePath = Paths.get(path);
                return filePath.toString();
            } catch (Exception e) {
                IO.println("Invalid file path! Try again.");
            }
        }
    }

    // This method validates user input file paths when importing.
    public static String validateImportPath() {

        while (true) {
            String path = validateFilePath("imports");

            File file = new File(path);

            if (!file.exists()) {
                IO.println("File does not exist.");
                continue;
            }

            return path;
        }
    }
}
