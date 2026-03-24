package se.lexicon.menu.options;

import se.lexicon.menu.MenuOption;

/**
 * Enum representing the options available in the "Import Contacts" submenu.
 * Implements MenuOption to allow integration with the generic menu system.
 */
public enum ImportMenuOption implements MenuOption {

    DEFAULT_IMPORT(1, "Import from default folder (data/imports/contacts.csv)"), // Import from predefined default path
    CUSTOM_IMPORT(2, "Define custom file path"),                                   // Import from user-specified path
    BACK(0, "Back to menu");                                                       // Go back to the previous menu

    private final int code;   // Numeric code used for menu selection
    private final String text; // Text displayed in the menu for this option

    ImportMenuOption(int code, String text) {
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