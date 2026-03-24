# 📇 Workshop : Contact Management App

A **Java console application** to manage contacts. The app allows you to **list all contacts**, 
**add**, **update**, **delete**, **search**, **sort**, **import/export contacts**, and **validates all user inputs**.

---

## 📝 Table of Contents

1. [✨ Features](#-features)
2. [📂 Folder Structure](#-folder-structure)
3. [🛠 Available Options](#-available-options)
4. [🗃 Data Structure](#-data-structure)
5. [✅ Input Validation](#-input-validation)
6. [🧪 JUnit Testing](#-junit-testing)
7. [🚀 How to Run](#-how-to-run)
8. [⚡ Expected Output :](#-expected-output-)
9. [📌 Workshop Document](#-workshop-document)
---

## 📌 Workshop Document

You can find the workshop description here:

[Workshop Document](Arrays-Collections-Workshop.md)

---
## ✨ Features

- ➕ Add new contacts with first name, last name, and mobile number
- ✏️ Update contact details (name, mobile, or both)
- 🗑 Delete contacts by name or mobile number
- 🔍 Search contacts by name or mobile number
- 📋 Display all contacts
- 🔢 Sort contacts alphabetically by name
- 💾 Export contacts to CSV (default folder or custom path)
- 📂 Import contacts from CSV (default folder or custom path)
- ⚠️ Confirmations before updating, deleting, or exporting
- 🛡 Robust input validation for all user inputs

---

## 📂 Folder Structure

```
ContactManagementApp/
│
├─ src/main/java/se/lexicon/
│  ├─ ContactApp.java                 # Entry point (main method)
│  ├─ operations/
│  │  └─ ContactOperations.java      # Core CRUD + CSV logic
│  ├─ service/
│  │  └─ ContactService.java         # Business logic, workflows, delete/update helpers
│  ├─ ui/
│  │  └─ UIHandler.java              # CLI display & user interaction
│  ├─ util/
│  │  └─ ContactValidator.java       # Input validation, path validation, y/n prompts
│  └─  menu/
│     ├─ Menu.java                   # Main menu & submenus
│     ├─ MenuTextBuilder.java        # Helper for building menu text
│     ├─ MenuOption.java             # Interface for menu enums
│     └─ options/                    # Menu enums
│        ├─ MainMenuOption.java
│        ├─ DeleteMenuOption.java
│        ├─ UpdateMenuOption.java
│        ├─ ExportMenuOption.java
│        └─ ImportMenuOption.java
├─ src/test/java/se/lexicon/
│  └─ ContactOperationsTest.java     # Unit tests for CRUD + CSV logic
├─ data/
│  ├─ imports/                        # Default import CSV folder
│  └─ exports/                        # Default export CSV folder
├─ README.md
└─ Arrays-Collections-Workshop.md
```
---
## 🛠 Available Options

### 🏠 Main Menu

1. ➕ Add new contact – Add a contact with validated name & phone.
2. 🔍 Search by name – Search by full or partial name.
3. 🔎 Search by mobile – Search by exact mobile number.
4. 📋 Display all contacts – List all stored contacts.
5. 🔢 Sort contacts by name – Sort alphabetically.
6. 🗑 Delete contact – Delete by name or mobile (submenu).
7. ✏️ Update contact – Update name, mobile, or both (submenu).
8. 💾 Export to CSV – Default folder or custom path.
9. 📂 Import from CSV – Default folder or custom path.
10. ❌ Exit – Quit the app.

### 🗑 Delete Submenu

1. Delete by Name
2. Delete by Mobile number
3. Back to main menu

### ✏️ Update Submenu

1. Update Name
2. Update Telephone number
3. Update Name and Telephone number
4. Back to main menu

---
## 🗃 Data Structure

- Contacts are stored in a ```HashMap<String, String>```:

  - Key → Contact name (FirstName LastName)
  - Value → Mobile number

- TreeMap is used for sorted display.
- HashMap provides fast search, update, delete operations.

---

## ✅ Input Validation

All validations are handled in ```Utility.java```:
- Name – Only letters, spaces, hyphens
- Mobile – Must be numeric & correct length (7-15 digits)
- Yes/No – Accepts y / n (case-insensitive)
- Menu options – Validated integer range
- File paths – Auto-creates directories if not present

---

## 🧪 JUnit Testing

```ContactOperationsTest.java``` covers:

- ➕ addContact()
- 🔍 searchByName() / searchByMobile()
- ✏️ updateName() / updateTelephoneNumber() / updateNameAndTelephone()
- 🗑 deleteByName() / deleteByTelephoneNumber()
- 📋 getAllContacts() / getSortedContacts()
- 💾 exportContactsToPath() / importsContactsFromPath()

**Notes**:
- Tests run independently with @BeforeEach clearing the HashMap.
- Temporary files used for import/export testing.

---
## 🚀 How to Run

1. Clone the repository.

2. Build with Maven:
    ```bash
    mvn clean install
    ```

3. Run the CLI:
   ```bash
    java -cp target/java-arrays-collections-workshop-1.0.jar se.lexicon.Main
    ```
   
4. Run tests:
    ```bash
    mvn test
    ```

---

## ⚡ Expected Output :

```
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
Choose option :  4
No contacts found!
Press ENTER to continue...
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
Choose option :  9
------------ Import from CSV ------------
1. Import from default folder (data/imports/contacts.csv)
2. Define custom file path
0. Back to menu
-----------------------------------------
Choose option :  2
Example 1: data/imports/contacts.csv
Example 2: C:/Users/JAYANI/Downloads/contacts.csv
Enter file path (.csv): C:/Users/JAYANI/Desktop/contacts.csv
Contact is imported successfully!
Press ENTER to continue...
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
Choose option :  4
------ Search Results (2) ------
1. Ranga Madunanda (0712555555)
2. Jayani Athukorala (0771231234)
Press ENTER to continue...
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
Choose option :  1
Enter contact Name: Jayani
Enter contact Number: 0111222333
------ Search Results (1) ------
1. Jayani Athukorala (0771231234)
Contact name or telephone already exists!
Do you want to try again ? (y/n) : y
Enter contact Name: Jayani Sandunka
Enter contact Number: 0111222333
Contact is add successfully!

```

---
