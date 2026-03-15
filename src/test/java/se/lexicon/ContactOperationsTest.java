package se.lexicon;

import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContactOperationsTest {

    @BeforeEach
    void setup() {
        // Clear contacts before each test
        ContactOperations.getAllContacts().clear();
    }

    @AfterEach
    void cleanup() {
        // Clear contacts after each test
        ContactOperations.getAllContacts().clear();
    }

    @Test
    void testSearchByName() {
        ContactOperations.addContact("John Smith", "0771234567");
        ContactOperations.addContact("Alice Johnson", "0712345678");

        Map<String, String> result = ContactOperations.searchByName("john");
        assertEquals(2, result.size()); // matches "John Smith" and "Alice Johnson"
    }

    @Test
    void testSearchByMobile() {
        ContactOperations.addContact("John Smith", "0771234567");

        Map<String, String> result = ContactOperations.searchByMobile("0771234567");
        assertEquals(1, result.size());
        assertTrue(result.containsKey("John Smith"));

        // test non-existing
        result = ContactOperations.searchByMobile("0000000000");
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateName() {
        ContactOperations.addContact("John Smith", "0771234567");
        boolean updated = ContactOperations.updateName("John Smith", "Johnny Smith");
        assertTrue(updated);
        assertFalse(ContactOperations.getAllContacts().containsKey("John Smith"));
        assertTrue(ContactOperations.getAllContacts().containsKey("Johnny Smith"));
    }

    @Test
    void testUpdateTelephoneNumber() {
        ContactOperations.addContact("John Smith", "0771234567");
        boolean updated = ContactOperations.updateTelephoneNumber("0771234567", "0712345678");
        assertTrue(updated);
        assertEquals("0712345678", ContactOperations.getAllContacts().get("John Smith"));
    }

    @Test
    void testDeleteByName() {
        ContactOperations.addContact("John Smith", "0771234567");
        boolean deleted = ContactOperations.deleteByName("John Smith");
        assertTrue(deleted);
        assertTrue(ContactOperations.getAllContacts().isEmpty());
    }

    @Test
    void testDeleteByTelephoneNumber() {
        ContactOperations.addContact("John Smith", "0771234567");
        boolean deleted = ContactOperations.deleteByTelephoneNumber("0771234567");
        assertTrue(deleted);
        assertTrue(ContactOperations.getAllContacts().isEmpty());
    }

    @Test
    void testGetSortedContacts() {
        ContactOperations.addContact("Charlie", "0771231234");
        ContactOperations.addContact("Alice", "0712345678");
        ContactOperations.addContact("Bob", "0751112233");

        Map<String, String> sorted = ContactOperations.getSortedContacts();
        String[] keys = sorted.keySet().toArray(new String[0]);
        assertArrayEquals(new String[]{"Alice", "Bob", "Charlie"}, keys);
    }

    @Test
    void testExportAndImportContacts() throws Exception {
        ContactOperations.addContact("John Smith", "0771234567");
        ContactOperations.addContact("Alice Johnson", "0712345678");

        File tempFile = Files.createTempFile("contacts_test", ".csv").toFile();
        tempFile.deleteOnExit();

        boolean exported = ContactOperations.exportContactsToPath(tempFile.getAbsolutePath());
        assertTrue(exported);

        // Clear contacts and import
        ContactOperations.getAllContacts().clear();
        boolean imported = ContactOperations.importsContactsFromPath(tempFile.getAbsolutePath());
        assertTrue(imported);

        assertTrue(ContactOperations.getAllContacts().containsKey("John Smith"));
        assertTrue(ContactOperations.getAllContacts().containsKey("Alice Johnson"));
    }
}
