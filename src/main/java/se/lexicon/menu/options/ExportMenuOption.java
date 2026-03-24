package se.lexicon.menu.options;

import se.lexicon.menu.MenuOption;

/**
 * Enum representing the options available in the "Export Contacts" submenu.
 * Implements MenuOption to allow integration with the generic menu display.
 */
public enum ExportMenuOption implements MenuOption {

    DEFAULT_EXPORT(1, "Save to default folder (data/exports/contacts.csv)"), // Export contacts to default path
    CUSTOM_EXPORT(2, "Define custom file path"),                               // Export contacts to user-specified path
    BACK(0, "Back to menu");                                                   // Return to the previous menu

    private final int code;   // Numeric code for menu selection
    private final String text; // Text to display in the menu

    ExportMenuOption(int code, String text) {
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