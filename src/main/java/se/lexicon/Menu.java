package se.lexicon;

import java.util.Map;
import java.util.Objects;

public class Menu {

    //This method displays the main menu and returns the user's chosen option
    static int displayMenu(){
        IO.print("""
                    ======== Contact Management App ========
                    1. Add New Contact
                    2. Search By Name
                    3. Search By Mobile
                    4. Display All Contacts
                    5. Sort Contacts By Name
                    6. Delete Contact
                    7. Update Contact
                    0. Exit
                    ========================================
                    Choose Option : \s""");
        return Utility.validateInt(0, 7);
    }

    //This method display given contact details.
    public static void displayHashMap(Map<String, String> contactList){
        if (contactList.isEmpty()) {
            IO.println("No contacts found!");
            return;
        }
        int number = 0;
        IO.println("\n--------- Contact Detail(s) -----------");
        for (Map.Entry<String, String> entry : contactList.entrySet()) {
            String name = entry.getKey();
            String mobile = entry.getValue();
            IO.println(++number+". "+name + " (" + mobile + ")");
        }
    }

    //This method displays all the contact details stored in app.
    public static void displayAllContacts() {
        Map<String, String> contactList = ContactOperations.getAllContacts();
        displayHashMap(contactList);
    }

    //This method will display warning add/update/delete contacts
    public static boolean displayWarning(Map<String, String> list, String type){
        boolean isContinue = false;
        displayHashMap(list);
        char yesNoChar = Utility.validateYesNo(type);
        if (yesNoChar == 'Y'){
            isContinue = true;
        }
        return isContinue;
    }

    //This method add the new contact to contacts hashmap
    static void addContactMenu() {
        String name = Utility.validateName("Contact");
        Map<String, String> contactForName, contactForMNo;
        contactForName= ContactOperations.searchByName(name);
        String phone = Utility.validateTelephoneNumber("Contact");
        contactForMNo = ContactOperations.searchByMobile(phone);
        contactForName.putAll(contactForMNo);
        if(contactForName.isEmpty()) {
            boolean status = ContactOperations.addContact(name, phone);

            if (status) {
                IO.println("Contact added successfully!");
            } else {
                IO.println("Problem in adding contact!");
            }
        }else {
            displayHashMap(contactForName);
            IO.println("Contact Name/Telephone Number already Exists!...Returning.....");
        }
    }

    //This method searches a contact detail by name or telephone number
    public static void search(String type){
        Map<String, String> contacts;
        String searchQuery;

        IO.println("Enter "+type+" you want to Search.....");
        if(Objects.equals(type, "Name")){
            searchQuery = Utility.validateName("Contact");
            contacts = ContactOperations.searchByName(searchQuery);
        }else{
            searchQuery = Utility.validateTelephoneNumber("Contact");
            contacts = ContactOperations.searchByMobile(searchQuery);
        }
        displayHashMap(contacts);
    }

    //This method sorts the contact details list by name
    public static void sortContacts(){
        Map<String, String> sortedContacts = ContactOperations.getSortedContacts();
        displayHashMap(sortedContacts);
    }

    //Delete submenu options
    public static void deleteContact() {
        IO.print("""
                ============ Delete Contact ============
                1. Delete Contact By Name
                2. Delete Contact By Mobile Number
                0. Back To Menu
                ========================================
                Choose Option : \s""");

        int deleteOption = Utility.validateInt(0, 2);

        switch (deleteOption) {
            case 1 -> deleteByName();
            case 2 -> deleteByMobile();
            case 0 -> { }
        }
    }

    //This method deletes a contact by name
    public static void deleteByName() {

        IO.println("Enter name of the contact you want to delete.....");
        String name = Utility.validateName("Contact");

        Map<String, String> nameList = ContactOperations.searchByName(name);
        if (!nameList.isEmpty()) {
            boolean continueTask = displayWarning(nameList, "delete");
            if (!continueTask) return;
            boolean deleteStatus = ContactOperations.deleteByName(name);
            displayStatus(deleteStatus, "deleted");
        }
    }

    //This method deletes a contact by phone number
    public static void deleteByMobile() {

        IO.println("Enter number of the contact you want to delete.....");

        String mobile = Utility.validateTelephoneNumber("Contact");

        Map<String, String> mobileList = ContactOperations.searchByMobile(mobile);
        if (!mobileList.isEmpty()) {
            boolean continueTask = displayWarning(mobileList, "delete");
            if (!continueTask) return;
            boolean deleteStatus = ContactOperations.deleteByTelephoneNumber(mobile);
            displayStatus(deleteStatus, "deleted");
        }
    }

    //Update Submenu
    public static void updateContact() {
        IO.print("""
            ============ Update Contact ============
            1. Update Name
            2. Update Telephone Number
            0. Back To Menu
            ========================================
            Choose Option : \s""");

        int updateOption = Utility.validateInt(0, 2);

        switch (updateOption) {
            case 1 -> updateName();
            case 2 -> updateTelephoneNumber();
            case 0 -> {}
        }
    }

    public static void updateName() {

        IO.println("Enter name of the contact you want to update.....");

        String currentName = Utility.validateName("Old");
        Map<String, String> nameList = ContactOperations.searchByName(currentName);
        if (!nameList.isEmpty()) {
            String newName = Utility.validateName("New");

            boolean continueTask = displayWarning(nameList, "update");

            if (!continueTask) return;

            boolean updateStatus = ContactOperations.updateName(currentName, newName);
            displayStatus(updateStatus, "updated");
        }
    }

    public static void updateTelephoneNumber() {

        IO.println("Enter telephone number of the contact you want to update.....");

        String currentMobile = Utility.validateTelephoneNumber("Old");
        Map<String, String> mobileList = ContactOperations.searchByMobile(currentMobile);
        if (!mobileList.isEmpty()) {
            String newMobile = Utility.validateTelephoneNumber("New");
            boolean continueTask = displayWarning(mobileList, "update");
            if (!continueTask) return;

            boolean updateStatus = ContactOperations.updateTelephoneNumber(currentMobile, newMobile);
            displayStatus(updateStatus, "updated");
        }
    }

    private static void displayStatus(boolean status, String type) {
        if (status) {
            IO.println("Contact is "+type+" successfully!");
        } else {
            IO.println("Attempt is unsuccessful!");
        }
    }
}
