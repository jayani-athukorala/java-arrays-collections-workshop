package se.lexicon;

import java.util.HashMap;
import java.util.Map;

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
    public boolean addContact(String name, String mobile) {

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
        searchQuery = searchQuery.trim();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String name = entry.getKey();
            String mobile = entry.getValue().trim();
            IO.println(name+mobile);
            if (mobile.contains(searchQuery)) {
                result.put(name, mobile);
            }
        }

        return result; // empty map if no match
    }
}
