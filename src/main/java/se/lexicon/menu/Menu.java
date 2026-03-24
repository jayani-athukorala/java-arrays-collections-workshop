package se.lexicon.menu;

import se.lexicon.menu.options.*;
import se.lexicon.service.ContactService;
import se.lexicon.ui.UIHandler;
import java.util.Scanner;

/**
 * Handles the navigation and interactions for the Contact App menu system.
 * Provides main menu and submenus for Add, Search, Display, Sort, Delete, Update, Export, and Import operations.
 * Delegates actual contact operations to ContactService and user I/O to UIHandler.
 */
public class Menu {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu and executes the selected option.
     *
     * @return The selected MainMenuOption (useful for EXIT handling)
     */
    public static MainMenuOption displayMainMenu() {
        MainMenuOption selected = UIHandler.showMenu("CONTACT APP - MAIN MENU", MainMenuOption.values());

        // Perform the action based on user selection
        switch (selected) {
            case ADD_CONTACT -> ContactService.addContact();                     // Add new contact
            case SEARCH_NAME -> UIHandler.search("Name");                         // Search by name
            case SEARCH_MOBILE -> UIHandler.search("Mobile");                     // Search by phone
            case DISPLAY_CONTACTS -> ContactService.displayAllContacts();         // Display all contacts
            case SORT_CONTACTS -> ContactService.sortContacts();                  // Sort contacts by name
            case DELETE_CONTACT -> Menu.deleteContact();                           // Show delete submenu
            case UPDATE_CONTACT -> Menu.updateContact();                           // Show update submenu
            case EXPORT_CONTACTS -> Menu.exportContacts();                         // Show export submenu
            case IMPORT_CONTACTS -> Menu.importContacts();                         // Show import submenu
            case EXIT -> {
                IO.println("Exiting the app...\nBye bye!");                       // Exit message
                return selected;
            }
        }

        pressEnterToContinue(); // Pause before returning to menu
        return selected;
    }

    /**
     * Displays the delete submenu and performs delete operations.
     * Delegates the actual delete action to ContactService.performDelete().
     */
    public static void deleteContact() {
        DeleteMenuOption deleteOption = UIHandler.showMenu("CONTACT APP - DELETE", DeleteMenuOption.values());

        switch (deleteOption) {
            case DELETE_BY_NAME -> ContactService.performDelete(
                    "Enter name of the contact you want to delete.....", true);
            case DELETE_BY_MOBILE -> ContactService.performDelete(
                    "Enter telephone number of the contact you want to delete.....", false);
        }
    }

    /**
     * Displays the update submenu and performs update operations.
     * Delegates the actual update action to ContactService.performUpdate().
     */
    public static void updateContact() {
        UpdateMenuOption updateOption = UIHandler.showMenu("CONTACT APP - UPDATE", UpdateMenuOption.values());

        switch (updateOption) {
            case UPDATE_NAME -> ContactService.performUpdate(true, false);         // Update only name
            case UPDATE_MOBILE -> ContactService.performUpdate(false, true);       // Update only phone
            case UPDATE_NAME_MOBILE -> ContactService.performUpdate(true, true);   // Update both name and phone
        }
    }

    /**
     * Displays the export submenu and exports contacts to CSV.
     * Delegates to ContactService.exportContactsToCSV() with default or custom path.
     */
    public static void exportContacts() {
        ExportMenuOption exportOption = UIHandler.showMenu("CONTACT APP - EXPORT", ExportMenuOption.values());

        switch (exportOption) {
            case DEFAULT_EXPORT -> ContactService.exportContactsToCSV(1);  // Export to default path
            case CUSTOM_EXPORT -> ContactService.exportContactsToCSV(2);   // Export to user-specified path
        }
    }

    /**
     * Displays the import submenu and imports contacts from CSV.
     * Delegates to ContactService.importContactsToCSV() with default or custom path.
     */
    public static void importContacts() {
        ImportMenuOption importOption = UIHandler.showMenu("CONTACT APP - IMPORT", ImportMenuOption.values());

        switch (importOption) {
            case DEFAULT_IMPORT -> ContactService.importContactsToCSV(1);  // Import from default path
            case CUSTOM_IMPORT -> ContactService.importContactsToCSV(2);   // Import from user-specified path
        }
    }

    /**
     * Prompts the user to press ENTER to continue.
     * Useful for pausing the console before returning to the main menu.
     */
    static void pressEnterToContinue() {
        IO.print("Press ENTER to continue...");
        scanner.nextLine(); // Consume leftover newline
    }
}