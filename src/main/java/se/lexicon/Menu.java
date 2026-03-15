package se.lexicon;

import java.util.Map;
import java.util.Objects;

public class Menu {

    private static final String ACTION_ADD = "add";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_CONTINUE = "continue";

    //This method displays the main menu and returns the user's chosen option
    static int displayMenu(){
        return showMenu("""
                 ======== Contact management menu ========
                 1. Add new contact
                 2. Search by name
                 3. Search by mobile
                 4. Display all contacts
                 5. Sort contacts by name
                 6. Delete contact
                 7. Update contact
                 8. Export to CSV
                 9. Import from CSV
                 0. Exit
                 ========================================
                 Choose option : \s""",9);
    }

    //This method display given contact details.
    public static void displayContacts(Map<String, String> contactList){
        if (contactList.isEmpty()) {
            IO.println("No contacts found!");
            return;
        }
        int[] counter = {1};

        IO.println("------ Search Results ("+contactList.size()+") ------");
        contactList.forEach((name, telephone) ->
                IO.println(counter[0]++ + ". " + name + " (" + telephone + ")"));
    }

    //This method displays all the contact details stored in app.
    public static void displayAllContacts() {
        Map<String, String> contactList = ContactOperations.getAllContacts();
        displayContacts(contactList);
    }

    private static int showMenu(String menuText, int max){
        IO.print(menuText);
        return Utility.validateInt(0, max);
    }

    //This method will display warning add/update/delete contacts
    public static boolean displayWarning(String type){
        String message = type.equals("continue")
                ? "Do you want to try again ? (y/n) : "
                : "Are you sure you want to " + type + " above contact/s ? (y/n) : ";

        return Utility.validateYesNo(message) == 'Y';
    }

    //This method add the new contact to contacts hashmap
    static void addContactMenu() {
        while (true) {
            String name = Utility.validateName("contact");
            Map<String, String> contactForName, contactForMNo;
            contactForName = ContactOperations.searchByName(name);

            String phone = Utility.validateTelephoneNumber("contact");
            contactForMNo = ContactOperations.searchByMobile(phone);
            contactForName.putAll(contactForMNo);
            if (!contactForName.isEmpty()) {
                displayContacts(contactForName);
                IO.println("Contact name or telephone already exists!");
                boolean status = displayWarning(ACTION_CONTINUE);
                if(!status) return;
            }else {
                boolean addStatus = ContactOperations.addContact(name, phone);
                displayStatus(addStatus, ACTION_ADD);
                return;
            }
        }
    }

    //This method searches a contact detail by name or telephone number
    public static void search(String type){
        Map<String, String> contacts;
        String searchQuery;

        IO.println("Enter the "+type+" to search.....");
        if(Objects.equals(type, "Name")){
            searchQuery = Utility.validateName("contact");
            contacts = ContactOperations.searchByName(searchQuery);
        }else{
            searchQuery = Utility.validateTelephoneNumber("contact");
            contacts = ContactOperations.searchByMobile(searchQuery);
        }
        displayContacts(contacts);
    }

    //This method sorts the contact details list by name
    public static void sortContacts(){
        Map<String, String> sortedContacts = ContactOperations.getSortedContacts();
        displayContacts(sortedContacts);
    }

    //Delete submenu options
    public static void deleteContact() {
        int deleteOption = showMenu("""
                ------------ Delete contact ------------
                1. Delete contact by name
                2. Delete contact by mobile number
                0. Back To Menu
                ----------------------------------------
                Choose option : \s""",2);

        switch (deleteOption) {
            case 1 -> deleteByName();
            case 2 -> deleteContactByTelephoneNumber();
            case 0 -> {}
        }
    }

    //This method deletes a contact by name
    public static void deleteByName() {

        while (true){
            IO.println("Enter name of the contact you want to delete.....");
            String name = Utility.validateName("Contact");

            Map<String, String> nameList = findByName(name);
            if(nameList == null) return;      // user chose not to continue
            if(nameList.isEmpty()) continue;

            displayContacts(nameList);
            if (nameList.size() != 1) {
                IO.println("Multiple contacts found!");
                boolean continueTask = displayWarning(ACTION_CONTINUE);
                if (!continueTask) return;
            }else {
                boolean continueTask = displayWarning(ACTION_DELETE);
                if (!continueTask) return;
                boolean deleteStatus = ContactOperations.deleteByName(name);
                displayStatus(deleteStatus, "deleted");
                return;
            }
        }
    }

    //This method deletes a contact by phone number
    public static void deleteContactByTelephoneNumber() {
        while(true) {
            IO.println("Enter telephone number of the contact you want to delete.....");
            String mobile = Utility.validateTelephoneNumber("contact");

            Map<String, String> mobileList = findByTelephone(mobile);
            if (mobileList == null) return;      // user chose not to continue
            if(mobileList.isEmpty()) continue;

            displayContacts(mobileList);
            boolean continueTask = displayWarning(ACTION_DELETE);
            if (!continueTask) return;
            boolean deleteStatus = ContactOperations.deleteByTelephoneNumber(mobile);
            displayStatus(deleteStatus, "deleted");
            return;
        }
    }

    //Update Submenu
    public static void updateContact() {

        int updateOption = showMenu("""
            ----------Update contact menu-----------
            1. Update name
            2. Update telephone number
            3. Update name and telephone number
            0. Back to menu
            ----------------------------------------
            Choose option : \s""",3);

        switch (updateOption) {
            case 1 -> updateContactName();
            case 2 -> updateTelephoneNumber();
            case 3 -> updateContactDetails();
            case 0 -> {}
        }
    }

    //Update name and telephone number of a contact
    private static void updateContactDetails() {
        while(true) {
            IO.println("Enter name and telephone number of the contact you want to update.....");

            String currentName = Utility.validateName("old");
            Map<String, String> contactForName, contactForMNo, newNameExists, newMobileExists;

            contactForName = findByName(currentName);
            if(contactForName == null) return;      // user chose not to continue
            if(contactForName.isEmpty()) continue;

            String currentMobile = Utility.validateTelephoneNumber("old");
            contactForMNo = findByTelephone(currentMobile);
            if(contactForMNo == null) return;      // user chose not to continue
            if(contactForMNo.isEmpty()) continue;

            contactForName.putAll(contactForMNo);

            if (contactForName.size() != 1) {
                displayContacts(contactForName);
                IO.println("Error! Name and mobile do not belong to the same contact.");
                boolean status = displayWarning(ACTION_CONTINUE);
                if(status) continue;
                return;
            }

            String newName = Utility.validateName("new");
            newNameExists = ContactOperations.searchByName(newName);
            if (!newNameExists.isEmpty()) {
                displayContacts(newNameExists);
                IO.println("Error! New name you entered belongs to other contact.");
                boolean status = displayWarning(ACTION_CONTINUE);
                if(status) continue;
                return;
            }

            String newMobile = Utility.validateTelephoneNumber("new");
            newMobileExists = ContactOperations.searchByMobile(newMobile);
            if (!newMobileExists.isEmpty()) {
                displayContacts(newMobileExists);
                IO.println("Error! New telephone number you entered belongs to other contact.");
                boolean status = displayWarning(ACTION_CONTINUE);
                if(status) continue;
                return;
            }
            displayContacts(contactForName);
            boolean continueTask = displayWarning(ACTION_UPDATE);
            if (!continueTask) return;

            boolean updateStatusName = ContactOperations.updateName(currentName, newName);
            boolean updateStatusMobile = ContactOperations.updateTelephoneNumber(currentMobile, newMobile);
            displayStatus(updateStatusName && updateStatusMobile, "updated");
            return;

        }
    }

    //This method update name of a contact
    public static void updateContactName() {

        while(true) {
            IO.println("Enter name of the contact you want to update.....");

            String currentName = Utility.validateName("old");
            Map<String, String> nameList, newNameList;
            nameList = findByName(currentName);
            if(nameList == null) return;      // user chose not to continue
            if(nameList.isEmpty()) continue;

            if (nameList.size() != 1) {
                displayContacts(nameList);
                IO.println("Error! You can't update multiple contacts at once.");
                boolean status = displayWarning(ACTION_CONTINUE);
                if(status) continue;
                return;
            }

            String newName = Utility.validateName("new");
            newNameList = ContactOperations.searchByName(newName);
            if (!newNameList.isEmpty()) {
                displayContacts(newNameList);
                IO.println("Error! The new name you entered already exists in contacts!");
                boolean status = displayWarning(ACTION_CONTINUE);
                if(status) continue;
                return;
            }
            displayContacts(nameList);
            boolean continueTask = displayWarning(ACTION_UPDATE);
            if (!continueTask) return;

            boolean updateStatus = ContactOperations.updateName(currentName, newName);
            displayStatus(updateStatus, "updated");
            return;
        }
    }

    //This method update phone number of a contact
    public static void updateTelephoneNumber() {

        while(true){
            IO.println("Enter telephone number of the contact you want to update.....");

            String currentMobile = Utility.validateTelephoneNumber("old");
            Map<String, String> mobileList = findByTelephone(currentMobile);
            if(mobileList == null) return;      // user chose not to continue
            if(mobileList.isEmpty()) continue;

            String newMobile = Utility.validateTelephoneNumber("new");
            displayContacts(mobileList);
            boolean continueTask = displayWarning(ACTION_UPDATE);
            if (!continueTask) return;

            boolean updateStatus = ContactOperations.updateTelephoneNumber(currentMobile, newMobile);
            displayStatus(updateStatus, "updated");
            return;
        }
    }

    private static Map<String, String> findByName(String name){
        Map<String, String> result = ContactOperations.searchByName(name);
        if(result.isEmpty()){
            IO.println("Error! '" + name + "' does not exist in the list.");
            boolean retry = displayWarning(ACTION_CONTINUE);
            if (!retry) return null;
        }
        return result;
    }

    private static Map<String, String> findByTelephone(String mobile){
        Map<String, String> result = ContactOperations.searchByMobile(mobile);

        if(result.isEmpty()){
            IO.println("Error! '" + mobile + "' does not exist in the list.");
            boolean retry = displayWarning(ACTION_CONTINUE);
            if (!retry) return null;
        }
        return result;
    }

    private static void displayStatus(boolean status, String type) {
        if (status) {
            IO.println("Contact is "+type+" successfully!");
        } else {
            IO.println("Attempt is unsuccessful!");
        }
    }

    //This method display export contacts sub menu
    public static void exportContacts(){
        IO.print("""
                ------------ Export to CSV-------------
                1. Save to default folder (data/exports/contacts.csv)
                2. Define custom file path
                0. Back to menu
                ---------------------------------------
                Choose option : \s""");
        int exportOption = Utility.validateInt(0, 2);
        switch (exportOption) {
            case 1 -> exportContactsToCSV(1);
            case 2 -> exportContactsToCSV(2);
            case 0 -> { }
        }
    }

    //This method exports the Contact list to CSV
    public static void exportContactsToCSV(int type){
        String filePath = "data/exports/contacts.csv";
        if(type == 2){
            filePath = Utility.validateFilePath("exports");
        }
        boolean exportStatus = ContactOperations.exportContactsToPath(filePath);
        displayStatus(exportStatus, "exported");
    }

    //This method display import contacts sub menu
    public static void importContacts(){
        IO.print("""
                ------------ Import from CSV ------------
                1. Import from default folder (data/imports/contacts.csv)
                2. Define custom file path
                0. Back to menu
                -----------------------------------------
                Choose option : \s""");
        int exportOption = Utility.validateInt(0, 2);
        switch (exportOption) {
            case 1 -> importContactsToCSV(1);
            case 2 -> importContactsToCSV(2);
            case 0 -> { }
        }
    }

    //This method imports the Contact list from CSV
    public static void importContactsToCSV(int type){
        String filePath = "data/imports/contacts.csv";
        if(type == 2){
            filePath = Utility.validateImportPath();
        }
        boolean importStatus = ContactOperations.importsContactsFromPath(filePath);
        displayStatus(importStatus, "imported");
    }
}
