package se.lexicon.menu.options;

import se.lexicon.menu.MenuOption;

/**
 * Enum representing the options available in the "Delete Contact" submenu.
 * Implements MenuOption to allow integration with the generic menu display.
 */
public enum DeleteMenuOption implements MenuOption {

    DELETE_BY_NAME(1, "Delete contact by name"),           // Delete a contact using the name
    DELETE_BY_MOBILE(2, "Delete contact by mobile number"), // Delete a contact using the phone number
    BACK(0, "Back to menu");                               // Return to the previous menu

    private final int code;   // Numeric code for menu selection
    private final String text; // Text to display in the menu

    DeleteMenuOption(int code, String text) {
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