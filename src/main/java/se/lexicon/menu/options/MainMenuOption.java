package se.lexicon.menu.options;

import se.lexicon.menu.MenuOption;

/**
 * Enum representing the options in the main menu of the Contact App.
 * Each option has a numeric code and display text.
 */
public enum MainMenuOption implements MenuOption {

    ADD_CONTACT(1, "Add new contact"),              // Option to add a new contact
    SEARCH_NAME(2, "Search by name"),               // Option to search contacts by name
    SEARCH_MOBILE(3, "Search by mobile"),           // Option to search contacts by phone number
    DISPLAY_CONTACTS(4, "Display all contacts"),    // Option to display all contacts
    SORT_CONTACTS(5, "Sort contacts by name"),     // Option to sort contacts alphabetically by name
    DELETE_CONTACT(6, "Delete contact"),           // Option to delete a contact
    UPDATE_CONTACT(7, "Update contact"),           // Option to update name/phone of a contact
    EXPORT_CONTACTS(8, "Export to CSV"),           // Option to export contacts to a CSV file
    IMPORT_CONTACTS(9, "Import from CSV"),         // Option to import contacts from a CSV file
    EXIT(0, "Exit");                               // Option to exit the application

    private final int code;   // Numeric code used for menu selection
    private final String text; // Text displayed in the menu for this option

    MainMenuOption(int code, String text) {
        this.code = code;
        this.text = text;
    }

    // Getter for menu selection code
    public int getCode() {
        return code;
    }

    // Getter for menu display text
    public String getText() {
        return text;
    }
}