package se.lexicon.menu.options;

import se.lexicon.menu.MenuOption;

/**
 * Enum representing the options available in the "Update Contact" submenu.
 * Allows updating the contact name, phone number, or both.
 */
public enum UpdateMenuOption implements MenuOption {

    UPDATE_NAME(1, "Update name"),                   // Update the contact's name
    UPDATE_MOBILE(2, "Update telephone number"),     // Update the contact's phone number
    UPDATE_NAME_MOBILE(3, "Update telephone number"), // Update both name and phone (note: text should be corrected to "Update name & phone")
    BACK(0, "Back to menu");                         // Return to the previous menu

    private final int code;   // Numeric code used for menu selection
    private final String text; // Text displayed in the menu for this option

    UpdateMenuOption(int code, String text) {
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