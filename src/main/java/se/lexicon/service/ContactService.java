package se.lexicon.service;

import se.lexicon.operations.ContactOperations;
import se.lexicon.ui.UIHandler;
import se.lexicon.util.ContactValidator;
import java.util.Map;

/**
 * Service class to manage all contact-related operations, including add, search, update, delete,
 * import/export, and validation. All UI output is delegated to UIHandler for consistency.
 */
public class ContactService {

    // Action constants for UI messages
    private static final String ACTION_ADD = "add";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_CONTINUE = "continue";

    /* ---------------------- Basic Listing & Sorting ---------------------- */

    /**
     * Display all contacts in the system.
     */
    public static void displayAllContacts() {
        Map<String, String> contactList = ContactOperations.getAllContacts();
        UIHandler.displayContacts(contactList);
    }

    /**
     * Display all contacts sorted alphabetically by name.
     */
    public static void sortContacts() {
        Map<String, String> sortedContacts = ContactOperations.getSortedContacts();
        UIHandler.displayContacts(sortedContacts);
    }

    /* ---------------------- Add Contact ---------------------- */

    /**
     * Add a new contact to the system with uniqueness checks for name and phone.
     * Loops until a valid and unique contact is entered or user cancels.
     */
    public static void addContact() {
        while (true) {
            String name = ContactValidator.getContactName("contact");
            String phone = ContactValidator.getContactPhone("contact");

            if (!isUnique(name, phone)) { // Check for duplicate name or phone
                if (!UIHandler.displayWarning(ACTION_CONTINUE)) return; // Exit if user cancels
                continue; // Retry input
            }

            boolean status = ContactOperations.addContact(name, phone);
            UIHandler.displayStatus(status, "added");
            return; // Exit loop after successful add
        }
    }

    /**
     * Check if a contact name and phone are unique in the system.
     *
     * @param name  Contact name
     * @param phone Contact phone
     * @return true if unique, false if duplicates exist
     */
    private static boolean isUnique(String name, String phone) {
        Map<String, String> existing = searchContact(name, true);
        existing.putAll(searchContact(phone, false));

        if (!existing.isEmpty()) {
            UIHandler.showDuplicate(existing);
            return false;
        }
        return true;
    }

    /* ---------------------- Search Contact ---------------------- */

    /**
     * Search a contact by name or phone with retry handling if not found.
     *
     * @param byName        true to search by name, false to search by phone
     * @param promptMessage Message to display to user before input
     * @return Map of matching contacts or null if user cancels
     */
    public static Map<String, String> searchWithRetry(boolean byName, String promptMessage) {
        while (true) {
            UIHandler.prompt(promptMessage);

            String input = byName
                    ? ContactValidator.getContactName("contact")
                    : ContactValidator.getContactPhone("contact");

            Map<String, String> results = searchContact(input, byName);

            if (!results.isEmpty()) return results; // Return if found

            UIHandler.showError("Error! '" + input + "' does not exist in the list.");
            if (!UIHandler.displayWarning(ACTION_CONTINUE)) return null; // Exit if user cancels
        }
    }

    /**
     * Search a contact by name or phone without retry.
     *
     * @param value  Name or phone
     * @param byName true if searching by name, false if searching by phone
     * @return Map of matching contacts
     */
    public static Map<String, String> searchContact(String value, boolean byName) {
        return byName
                ? ContactOperations.searchByName(value)
                : ContactOperations.searchByMobile(value);
    }

    /* ---------------------- Get Unique Value ---------------------- */

    /**
     * Prompt user for a new contact value (name or phone) that must be unique in the system.
     *
     * @param isName      true for name, false for phone
     * @param promptType  prompt type to display ("new" or "old")
     * @param errorMessage Message to display if duplicate found
     * @return unique value or null if user cancels
     */
    public static String getUniqueValue(boolean isName, String promptType, String errorMessage) {
        while (true) {
            String value = isName
                    ? ContactValidator.getContactName(promptType)
                    : ContactValidator.getContactPhone(promptType);

            Map<String, String> existing = searchContact(value, isName);

            if (existing.isEmpty()) return value; // Return if unique

            UIHandler.showDuplicate(existing);
            UIHandler.showError(errorMessage);

            if (!UIHandler.displayWarning(ACTION_CONTINUE)) return null; // Exit if user cancels
        }
    }

    /* ---------------------- Delete Contact ---------------------- */

    /**
     * Generic delete operation for contacts by name or phone.
     * Handles retries, multiple matches, and confirmation prompts.
     *
     * @param promptMessage Message to display for user input
     * @param byName        true to delete by name, false to delete by phone
     */
    public static void performDelete(String promptMessage, boolean byName) {
        boolean deleted = false;

        while (!deleted) {
            Map<String, String> results = searchWithRetry(byName, promptMessage);
            if (results == null) return; // User cancelled

            UIHandler.displayContacts(results);

            if (results.size() > 1 && byName) {
                UIHandler.showError("Multiple contacts found!");
                if (!UIHandler.displayWarning(ACTION_CONTINUE)) return; // Cancel loop
                continue; // Retry
            }

            if (!UIHandler.displayWarning(ACTION_DELETE)) {
                if (UIHandler.displayWarning(ACTION_CONTINUE)) continue; // Retry if user wants
                return; // Exit if user cancels
            }

            boolean status = byName
                    ? ContactOperations.deleteByName(results.keySet().iterator().next())
                    : ContactOperations.deleteByTelephoneNumber(results.keySet().iterator().next());

            UIHandler.displayStatus(status, "deleted");

            if (status) deleted = true; // Exit loop on successful deletion
            else if (!UIHandler.displayWarning(ACTION_CONTINUE)) return; // Retry or exit
        }
    }

    /* ---------------------- Update Contact ---------------------- */

    /**
     * Generic update operation for contacts.
     * Can update name, phone, or both. Handles retries, uniqueness, and confirmations.
     *
     * @param updateName  true to update name
     * @param updatePhone true to update phone
     */
    public static void performUpdate(boolean updateName, boolean updatePhone) {
        while (true) {
            UIHandler.prompt("Enter current details of the contact you want to update.....");

            String currentName = updateName || updatePhone ? ContactValidator.getContactName("old") : null;
            String currentPhone = updatePhone || updateName ? ContactValidator.getContactPhone("old") : null;

            Map<String, String> nameResults = currentName != null ? searchContact(currentName, true) : Map.of();
            Map<String, String> phoneResults = currentPhone != null ? searchContact(currentPhone, false) : Map.of();

            Map<String, String> results = !nameResults.isEmpty() ? nameResults : phoneResults;
            if (results.isEmpty()) {
                UIHandler.showError("Contact not found!");
                if (!UIHandler.displayWarning(ACTION_CONTINUE)) return;
                continue;
            }

            UIHandler.displayContacts(results);

            String newName = null, newPhone = null;

            if (updateName) {
                newName = getUniqueValue(true, "new", "Error! Name already exists!");
                if (newName == null) return;
            }
            if (updatePhone) {
                newPhone = getUniqueValue(false, "new", "Error! Phone already exists!");
                if (newPhone == null) return;
            }

            if (!UIHandler.displayWarning(ACTION_UPDATE)) return;

            boolean statusName = true, statusPhone = true;
            if (updateName) statusName = ContactOperations.updateName(currentName, newName);
            if (updatePhone) statusPhone = ContactOperations.updateTelephoneNumber(currentPhone, newPhone);

            UIHandler.displayStatus(statusName && statusPhone, "updated");
            return;
        }
    }

    /* ---------------------- Import & Export ---------------------- */

    /**
     * Export contacts to a CSV file.
     *
     * @param type 1 = default path, 2 = custom path
     */
    public static void exportContactsToCSV(int type) {
        String filePath = "data/exports/contacts.csv";
        if (type == 2) {
            filePath = ContactValidator.validateFilePath("exports");
        }
        boolean exportStatus = ContactOperations.exportContactsToPath(filePath);
        UIHandler.displayStatus(exportStatus, "exported");
    }

    /**
     * Import contacts from a CSV file.
     *
     * @param type 1 = default path, 2 = custom path
     */
    public static void importContactsToCSV(int type) {
        String filePath = "data/imports/contacts.csv";
        if (type == 2) {
            filePath = ContactValidator.validateImportPath();
        }
        boolean importStatus = ContactOperations.importsContactsFromPath(filePath);
        UIHandler.displayStatus(importStatus, "imported");
    }
}