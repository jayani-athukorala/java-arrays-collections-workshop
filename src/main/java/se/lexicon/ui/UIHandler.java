package se.lexicon.ui;

import se.lexicon.service.ContactService;
import se.lexicon.util.ContactValidator;
import se.lexicon.menu.MenuOption;
import se.lexicon.menu.MenuTextBuilder;
import java.util.Map;

/**
 * Handles all user interface interactions for the Contact application.
 * Includes menu display, contact listings, prompts, warnings, and status messages.
 * Decouples the service logic from console I/O.
 */
public class UIHandler {

    /**
     * Displays a menu with given title and options, and returns the user's selection.
     *
     * @param title   The title of the menu
     * @param options Array of Enum options that implement MenuOption
     * @param <T>     Enum type extending MenuOption
     * @return The option selected by the user
     */
    public static <T extends Enum<T> & MenuOption> T showMenu(
            String title,
            T[] options
    ) {

        // Determine the min and max code for input validation
        int min = options[0].getCode();
        int max = options[0].getCode();

        for (T option : options) {
            if (option.getCode() < min) min = option.getCode();
            if (option.getCode() > max) max = option.getCode();
        }

        // Build and display the menu
        String menuText = MenuTextBuilder.buildMenu(title, options);
        IO.print(menuText);

        // Validate user input and get the selected menu option
        int choice = ContactValidator.validateInt(min, max);
        for (T option : options) {
            if (option.getCode() == choice) {
                return option;
            }
        }

        throw new IllegalStateException("Invalid menu selection");
    }

    /**
     * Displays a list of contacts in a readable format.
     *
     * @param contactList Map of contact names and phone numbers
     */
    public static void displayContacts(Map<String, String> contactList) {
        if (contactList.isEmpty()) {
            IO.println("No contacts found!");
            return;
        }

        IO.println("------ Search Results (" + contactList.size() + ") ------");
        int counter = 1;
        for (Map.Entry<String, String> entry : contactList.entrySet()) {
            IO.println(counter++ + ". " + entry.getKey() + " (" + entry.getValue() + ")");
        }
    }

    /**
     * Prompts the user to input a search query for name or phone.
     * Fetches and displays matching contacts.
     *
     * @param type "Name" or "Phone" to specify search type
     */
    public static void search(String type) {
        IO.println("Enter the " + type + " to search.....");
        boolean byName = "Name".equalsIgnoreCase(type);
        String query = byName
                ? ContactValidator.getContactName("contact")
                : ContactValidator.getContactPhone("contact");

        Map<String, String> contacts = ContactService.searchContact(query, byName);
        displayContacts(contacts);
    }

    /**
     * Displays a warning message for confirmation (e.g., continue, delete, update).
     *
     * @param type Type of action ("continue", "add", "delete", "update")
     * @return true if user confirms with 'Y', false if 'N'
     */
    public static boolean displayWarning(String type) {
        String message = type.equals("continue")
                ? "Do you want to try again ? (y/n) : "
                : "Are you sure you want to " + type + " above contact/s ? (y/n) : ";

        return ContactValidator.validateYesNo(message) == 'Y';
    }

    /**
     * Displays the status of an operation (success or failure).
     *
     * @param status true if operation succeeded, false otherwise
     * @param type   Operation type ("added", "deleted", "updated")
     */
    public static void displayStatus(boolean status, String type) {
        if (status) {
            IO.println("Contact is " + type + " successfully!");
        } else {
            IO.println("Attempt is unsuccessful!");
        }
    }

    /**
     * Displays a message prompt to the user.
     *
     * @param message Message to show
     */
    public static void prompt(String message) {
        IO.println(message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message text
     */
    public static void showError(String message) {
        IO.println(message);
    }

    /**
     * Displays duplicate contacts and a warning message.
     *
     * @param contacts Map of duplicate contacts
     */
    public static void showDuplicate(Map<String, String> contacts) {
        displayContacts(contacts);
        IO.println("Contact already exists!");
    }
}