package se.lexicon;

public class Main {
    static void main() {

        int chosenOption;
        do {
            chosenOption = Menu.displayMenu();

            switch (chosenOption) {
                case 1:
                    Menu.addContactMenu();
                    break;
                case 2:
                    Menu.search("Name");
                    break;
                case 3:
                    Menu.search("Mobile");
                    break;
                case 4:
                    Menu.displayAllContacts();
                    break;
                case 5:
                    Menu.sortContacts();
                    break;
                case 6:
                    Menu.deleteContact();
                    break;
                default:
                    IO.println("Exiting application...");
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
