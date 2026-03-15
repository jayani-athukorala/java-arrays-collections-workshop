package se.lexicon;

public class Main {
    static void main() {

        int chosenOption;
        do {
            chosenOption = Menu.displayMenu();

            switch (chosenOption) {
                case 1 -> Menu.addContactMenu();
                case 2 -> Menu.search("Name");
                case 3 -> Menu.search("Mobile");
                case 4 -> Menu.displayAllContacts();
                case 5 -> Menu.sortContacts();
                case 6 -> Menu.deleteContact();
                case 7 -> Menu.updateContact();
                case 8 -> Menu.exportContacts();
                case 9 -> Menu.importContacts();
                default -> IO.println("Exiting application...");
            }
            if (chosenOption != 0) {
                pressEnterToContinue();
            }
        }while (chosenOption != 0); //Repeat until Exit is selected.
    }

    static void pressEnterToContinue() {
        IO.print("Press ENTER to continue...");
        Utility.scanner.nextLine(); // clear leftover newline
    }
}
