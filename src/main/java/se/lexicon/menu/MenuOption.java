package se.lexicon.menu;

/**
 * Interface for menu options used in the generic menu system.
 * All enums representing menu options implement this interface.
 */
public interface MenuOption {
    /**
     * Returns the numeric code for this menu option.
     * Used to match user input with the option.
     */
    int getCode();

    /**
     * Returns the text description of this menu option.
     * Displayed to the user in the menu.
     */
    String getText();
}