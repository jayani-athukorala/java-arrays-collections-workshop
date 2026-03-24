package se.lexicon.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for validating user input related to contacts.
 * Includes validation for integers, names, phone numbers, yes/no choices, and file paths.
 */
public class ContactValidator {

    static Scanner scanner = new Scanner(System.in);

    /* ---------------------- Numeric Input Validation ---------------------- */

    /**
     * Validate integer input from user within a specified range.
     * Loops until a valid integer is entered.
     *
     * @param min Minimum acceptable value
     * @param max Maximum acceptable value
     * @return validated integer input
     */
    public static int validateInt(int min, int max) {
        int option;
        while (true) {
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                // Check if input is within range
                if (option < min || option > max) {
                    throw new IllegalArgumentException();
                }

                break; // Valid input
            } catch (InputMismatchException e) {
                System.out.print("Invalid Option! Please enter a valid number (" + min + "-" + max + "): ");
                scanner.nextLine(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.print("Option out of range! Enter a number between " + min + " and " + max + ": ");
            }
        }
        return option;
    }

    /* ---------------------- Name Validation ---------------------- */

    /**
     * Validate contact name input.
     * Ensures only letters and spaces are allowed and input is non-empty.
     *
     * @param type Describes the type of name (e.g., "contact", "old", "new")
     * @return validated contact name
     */
    public static String getContactName(String type) {
        String name;
        while (true) {
            System.out.print("Enter " + type + " Name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                System.out.println("Invalid name! Only letters and spaces are allowed.");
            } else {
                break; // Valid name
            }
        }
        return name;
    }

    /* ---------------------- Phone Number Validation ---------------------- */

    /**
     * Validate contact phone number input.
     * Ensures the number contains only digits and is 7-15 characters long.
     *
     * @param type Describes the type of number (e.g., "contact", "old", "new")
     * @return validated phone number
     */
    public static String getContactPhone(String type) {
        String phone;
        while (true) {
            System.out.print("Enter " + type + " Number: ");
            phone = scanner.nextLine().trim();

            if (!phone.matches("\\d{7,15}")) {
                System.out.println("Invalid phone number! Enter 7-15 digits only. Try again.");
            } else {
                break; // Valid phone number
            }
        }
        return phone;
    }

    /* ---------------------- Yes/No Input Validation ---------------------- */

    /**
     * Validate a yes/no (Y/N) input from the user.
     *
     * @param message Prompt message to display
     * @return 'Y' or 'N' character
     */
    public static char validateYesNo(String message) {
        while (true) {
            IO.print(message);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y") || input.equals("N")) {
                return input.charAt(0);
            }

            IO.println("Invalid input! Enter 'Y' or 'N'.");
        }
    }

    /* ---------------------- File Path Validation ---------------------- */

    /**
     * Validate a CSV file path input from the user.
     * Checks for non-empty string, correct CSV extension, and valid path format.
     *
     * @param type Type of file ("exports" or "imports") for prompt purposes
     * @return validated file path string
     */
    public static String validateFilePath(String type) {

        IO.print(String.format("""
                Example 1: data/%s/contacts.csv
                Example 2: C:/Users/JAYANI/Downloads/contacts.csv
                """, type));

        while (true) {
            IO.print("Enter file path (.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                IO.println("Path cannot be empty.");
                continue;
            }

            if (!path.toLowerCase().endsWith(".csv")) {
                IO.println("File must have .csv extension.");
                continue;
            }

            // Validate that path is properly formatted
            try {
                Path filePath = Paths.get(path);
                return filePath.toString();
            } catch (Exception e) {
                IO.println("Invalid file path! Try again.");
            }
        }
    }

    /**
     * Validate file path specifically for importing contacts.
     * Ensures the file exists before returning.
     *
     * @return validated import file path
     */
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