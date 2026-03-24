package se.lexicon.operations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class responsible for CRUD operations on the contact list.
 * Contacts are stored in a static HashMap in memory.
 */
public class ContactOperations {

    // In-memory storage for contacts: key = name, value = phone number
    private static final Map<String, String> contacts = new HashMap<>();

    // =========================
    // Get All Contacts
    // =========================
    /**
     * Returns all contacts in the system.
     * @return Map of all contacts
     */
    public static Map<String, String> getAllContacts() {
        return contacts;
    }

    // =========================
    // Add New Contact
    // =========================
    /**
     * Adds a new contact to the system.
     * @param name Contact name
     * @param mobile Contact phone number
     * @return true if added successfully
     */
    public static boolean addContact(String name, String mobile) {
        contacts.put(name, mobile);
        return true;
    }

    // =========================
    // Search by Name
    // =========================
    /**
     * Searches contacts whose name contains the search query (case-insensitive).
     * @param searchQuery Query string
     * @return Map of matching contacts
     */
    public static Map<String, String> searchByName(String searchQuery) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (entry.getKey().toLowerCase().contains(searchQuery.toLowerCase())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    // =========================
    // Search by Mobile Number
    // =========================
    /**
     * Searches contacts by exact phone number match.
     * @param searchQuery Phone number to search
     * @return Map of matching contacts
     */
    public static Map<String, String> searchByMobile(String searchQuery) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (Objects.equals(entry.getValue(), searchQuery)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    // =========================
    // Sort Contacts
    // =========================
    /**
     * Returns a TreeMap of contacts sorted alphabetically by name.
     */
    public static Map<String, String> getSortedContacts() {
        return new TreeMap<>(contacts);
    }

    // =========================
    // Delete Contact by Name
    // =========================
    /**
     * Deletes a contact by name.
     * @param name Name of contact to delete
     * @return true if deletion was successful
     */
    public static boolean deleteByName(String name) {
        contacts.remove(name);
        return true;
    }

    // =========================
    // Delete Contact by Mobile Number
    // =========================
    /**
     * Deletes a contact by phone number.
     * @param mobile Phone number of contact to delete
     * @return true if deletion was successful
     */
    public static boolean deleteByTelephoneNumber(String mobile) {
        String keyToRemove = null;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (entry.getValue().equals(mobile)) {
                keyToRemove = entry.getKey();
                break;
            }
        }
        if (keyToRemove != null) {
            contacts.remove(keyToRemove);
            return true;
        }
        return false;
    }

    // =========================
    // Update Contact Name
    // =========================
    /**
     * Updates a contact's name while keeping the same phone number.
     * @param currentName Current name of contact
     * @param newName New name
     * @return true if update successful
     */
    public static boolean updateName(String currentName, String newName) {
        String telephone = contacts.get(currentName);
        contacts.remove(currentName);
        contacts.put(newName, telephone);
        return true;
    }

    // =========================
    // Update Contact Number
    // =========================
    /**
     * Updates a contact's phone number.
     * @param currentMobile Current phone number
     * @param newMobile New phone number
     * @return true if update successful
     */
    public static boolean updateTelephoneNumber(String currentMobile, String newMobile) {
        for (Map.Entry<String, String> contact : contacts.entrySet()) {
            if (Objects.equals(contact.getValue(), currentMobile)) {
                contact.setValue(newMobile);
                return true;
            }
        }
        return false;
    }

    // =========================
    // Export Contacts To CSV
    // =========================
    /**
     * Exports all contacts to the given CSV file path.
     * @param filePath File path to export to
     * @return true if export successful
     */
    public static boolean exportContactsToPath(String filePath) {
        try {
            Path file = Paths.get(filePath);
            Path parent = file.getParent();

            // Create directories if they don't exist
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, String> entry : contacts.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // Import Contacts From CSV
    // =========================
    /**
     * Imports contacts from the given CSV file path.
     * @param filePath File path to import from
     * @return true if import successful
     */
    public static boolean importsContactsFromPath(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String mobile = parts[1].trim();
                    contacts.put(name, mobile);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}