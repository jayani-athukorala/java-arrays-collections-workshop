package se.lexicon;

import se.lexicon.menu.Menu;
import se.lexicon.menu.options.MainMenuOption;

/**
 * Entry point for the Contact App.
 * Launches the main menu and keeps the application running until the user chooses EXIT.
 */
public class ContactApp {

    /**
     * Main method to start the Contact App.
     * Continuously displays the main menu until the user selects EXIT.
     */
    static void main() {
        MainMenuOption chosenOption;

        // Loop through the main menu until user chooses EXIT
        do {
            chosenOption = Menu.displayMainMenu();
        } while (chosenOption != MainMenuOption.EXIT);
    }
}