package se.lexicon;

import java.util.Scanner;

public class Utility {
    static Scanner scanner = new Scanner(System.in);
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
            }catch (Exception e){
                scanner.nextLine(); // clear invalid input from buffer
                System.out.print("Invalid Option! Please enter a valid number (" + min + "-" + max + "): ");
            }
        }
        return option;
    }

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

    public static char validateYesNo(String type) {
        while (true) {
            IO.print("Are you sure you want to "+type+" above contact/s ? (y/n) : ");
            String input = scanner.next().trim().toUpperCase();

            if (input.equals("Y") || input.equals("N")) {
                return input.charAt(0);
            }

            IO.println("Invalid input!");
        }
    }
}
