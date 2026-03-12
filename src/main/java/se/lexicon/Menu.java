package se.lexicon;

import java.util.Map;
import java.util.Objects;

public class Menu {
    static int displayMenu(){
        IO.print("""
                    ======== Contact Management App ========
                    1. Add New Contact
                    2. Search by Name
                    3. Search by Mobile
                    4. Display All Contacts
                    5. Sort Contacts By Name
                    0. Exit
                    ========================================
                    """);
        IO.print("Choose Option : ");
        return Utility.validateInt(0, 5);
    }

    static void addContactMenu() {
        String name = Utility.validateName();

        String phone = Utility.validateTelephoneNumber();

        ContactOperations contactOperations = new ContactOperations();
        boolean status = contactOperations.addContact(name, phone);

        if(status) {
            IO.println("Contact added successfully!");
        }else{
            IO.println("Problem in adding contact!");
        }

    }

    public static void displayAllContacts() {

        Map<String, String> contacts = ContactOperations.getAllContacts();
        if (contacts.isEmpty()) {
            IO.println("No contacts available.");
        }else {
            IO.println("\n=== Contact List ===");

            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String name = entry.getKey();
                String mobile = entry.getValue();

                IO.println(name + " (" + mobile + ")");
            }
        }
    }

    public static void search(String type){

        Map<String, String> contacts;
        String searchQuery;

        IO.println("Enter "+type+" you want to Search.....");
        if(Objects.equals(type, "Name")){
            searchQuery = Utility.validateName();
            contacts = ContactOperations.searchByName(searchQuery);
        }else{
            searchQuery = Utility.validateTelephoneNumber();
            contacts = ContactOperations.searchByMobile(searchQuery);
        }

        if (contacts.isEmpty()) {
            IO.println("No contacts found matching \"" + searchQuery + "\"");
        }else {
            IO.println("\n=== Search Results for "+type+" = "+searchQuery+" ===");
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String name = entry.getKey();
                String mobile = entry.getValue();
                IO.println(name + " (" + mobile + ")");
            }
        }
    }

    public static void sortContacts(){
        Map<String, String> sortedContacts;

        sortedContacts = ContactOperations.getSortedContacts();
        if(sortedContacts.isEmpty()){
            IO.println("No contacts available.");
        }else{
            IO.println("\n=== Sorted Contact List By Name ===");
            for (Map.Entry<String, String> entry : sortedContacts.entrySet()) {
                String name = entry.getKey();
                String mobile = entry.getValue();
                IO.println(name + " (" + mobile + ")");
            }
        }
    }
}
