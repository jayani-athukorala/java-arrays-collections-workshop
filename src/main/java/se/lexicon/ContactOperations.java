package se.lexicon;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ContactOperations {

    // Store contacts here (shared across methods)
    private static final Map<String, String> contacts = new HashMap<>();

    // =========================
    // Get All Contacts
    // =========================
    public static Map<String, String> getAllContacts() {
        return contacts; // returns Map<String,String>
    }

    // =========================
    // Add New Contact
    // =========================
    public static boolean addContact(String name, String mobile) {

        contacts.put(name, mobile);
        return true;
    }

    // =========================
    // Search by Name
    // =========================
    public static Map<String, String> searchByName(String searchQuery) {
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String name = entry.getKey();
            String mobile = entry.getValue();

            if (name.toLowerCase().contains(searchQuery.toLowerCase())) {
                result.put(name, mobile);
            }
        }
        return result;
    }

    // =========================
    // Search by Mobile Number
    // =========================
    public static Map<String, String> searchByMobile(String searchQuery) {
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String name = entry.getKey();
            String mobile = entry.getValue();

            if (Objects.equals(mobile, searchQuery)) {
                result.put(name, mobile);
            }
        }

        return result;
    }

    // =========================
    // Sort Contacts
    // =========================
    public static Map<String, String> getSortedContacts() {
        return new TreeMap<>(contacts);
    }

    // =========================
    // Delete Contact by Name
    // =========================
    public static boolean deleteByName(String name) {
        contacts.remove(name);
        return true;
    }

    // =========================
    // Delete Contact by Mobile Number
    // =========================
    public static boolean deleteByTelephoneNumber(String mobile) {
        String keyToRemove = null;

        for (Map.Entry<String,String> entry : contacts.entrySet()) { //Loop through the map get the key
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
    public static boolean updateName(String currentName, String newName) {

        String telephone = contacts.get(currentName);
        contacts.remove(currentName);
        contacts.put(newName, telephone);

        return true;
    }

    // =========================
    // Update Contact Number
    // =========================
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
    public static boolean exportContactsToPath(String filePath) {
        try {
            Path file = Paths.get(filePath);
            Path parent = file.getParent();

            if(parent != null && !Files.exists(parent)){
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
