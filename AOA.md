# Library Membership System 

**📊 Presentation**
You can view the project presentation on Canva here:
👉 https://www.canva.com/design/DAGksBNIwZk/Jjb_-j4b1bhjqU1hJ5dcFg/edit?utm_content=DAGksBNIwZk&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

## Overview

The Library Membership System is a console application developed in Java for managing library member information and tracking borrowed books. It provides functionalities to register new members, view their details, update their information, delete members, display all members, and search for members by name. Additionally, the system allows recording which books a member has borrowed and persists this data using a CSV file.

## Features

* **Register New Member:** Add a new member to the library system, including their unique ID, name, contact details, and the option to record an initial borrowed book.
* **View Member Information:** Display the complete details of a library member, including their ID, name, contact information, address, and a list of any currently borrowed books.
* **Update Member Information:** Modify the contact details (name, email, phone number, address) of an existing member.
* **Delete Member:** Remove a member's record from the library system using their unique ID.
* **Display All Members:** List all currently registered members, showing their basic information and any books they have borrowed.
* **Find Member by Name:** Search for and display members whose first or last name matches a given query (case-insensitive).
* **Borrow Book:** Record a book as borrowed by a specific member, associating the book title and borrow date with the member's record.
* **Save Data:** Persist the current library member data, including borrowed book information, to a CSV file (`members.csv`).
* **Load Data:** Automatically load library member data, including borrowed books, from the `members.csv` file when the application starts.
* **Generate Report:** Generate a basic report that currently displays the total number of registered members.
* **Exit:** Close the Library Membership System application.

## Prerequisites

* **Java Development Kit (JDK)** version 8 or higher must be installed on your system.

The application stores and retrieves data from a CSV file named `members.csv`, located in the same directory as the compiled Java class. This file will be automatically created if it doesn't exist.

## Running the Application

1.  **Clone or download** the `LibraryMembershipSystem.java` file to your local machine.
2.  **Open your terminal or command prompt** and navigate to the directory where you saved the file.
3.  **Compile** the Java code using the command:
    ```bash
    javac LibraryMembershipSystem.java
    ```
4.  **Run** the application with the command:
    ```bash
    java LibraryMembershipSystem
    ```

This will launch the Library Membership System in the terminal, and you can interact with the menu options by entering the corresponding numbers.

## Functions and Methods

1.  **`registerNewMember(List<MemberInfo> members, Scanner scanner)`:**
    * Prompts the user to enter details for a new member: ID (digits only), first name (letters only), last name (letters only), email (must end with "@gmail.com"), phone number (digits only), and address.
    * Offers an option to record a book being borrowed at the time of registration.
    * Creates a new `MemberInfo` object and adds it to the `members` list.

2.  **`viewMemberInfo(List<MemberInfo> members, Scanner scanner)`:**
    * Asks the user to enter the member ID to view.
    * Searches the `members` list for a member with the matching ID and displays their information, including any books they have borrowed.
    * Shows a "member not found" message if the ID is invalid.

3.  **`updateMemberInfo(List<MemberInfo> members, Scanner scanner)`:**
    * Prompts the user for the ID of the member to update.
    * If the member is found, it allows the user to enter new values for first name, last name, email, phone number, and address. Leaving an input blank retains the existing value.

4.  **`deleteMember(List<MemberInfo> members, Scanner scanner)`:**
    * Asks the user to enter the ID of the member to delete.
    * Removes the member with the specified ID from the `members` list.
    * Displays a success or "member not found" message.

5.  **`displayAllMembers(List<MemberInfo> members)`:**
    * Iterates through the `members` list and prints the details of each member, including any borrowed books.
    * Displays a message if there are no registered members.

6.  **`findMemberByName(List<MemberInfo> members, Scanner scanner)`:**
    * Prompts the user to enter a name to search for.
    * Searches the `members` list and displays all members whose first or last name contains the search term (case-insensitive).

7.  **`saveMembersToFile(List<MemberInfo> members)`:**
    * Writes the data of all members in the `members` list to the `members.csv` file.
    * Each member's information, including their borrowed books, is formatted as a comma-separated string.

8.  **`loadMembersFromFile()`:**
    * Reads member data from the `members.csv` file when the application starts.
    * Parses each line to create `MemberInfo` objects and adds them to the `members` list.
    * Handles potential `IOException` if the file is not found or cannot be read.

9.  **`generateReport(List<MemberInfo> members)`:**
    * Generates a basic report showing the total number of registered members.

10. **`borrowBook(List<MemberInfo> members, Scanner scanner)`:**
    * Prompts the user for the ID of the member borrowing a book and the title of the book.
    * Finds the member by ID and adds the book title and the current date to the member's list of borrowed books.

11. **`findMemberById(List<MemberInfo> members, String id)`:**
    * A helper method to find a `MemberInfo` object in the `members` list by their ID.

12. **`isValidEmail(String email)`, `isValidName(String name)`, `isValidPhone(String phone)`, `isValidId(String id)`:**
    * Helper methods to validate the format of user input for email, name, phone number, and ID.

  ## Error Handling

The application performs input validation during member registration to ensure the correct format for member ID (digits only), first and last names (letters only), email (ends with "@gmail.com"), and phone number (digits only). If invalid input is provided, the user is prompted to re-enter the information. For operations involving member IDs (view, update, delete, borrow book), if an invalid or non-existent ID is entered, the application displays a "member not found" message.

## File Format

The `members.csv` file stores member data with each line representing one member. The fields are comma-separated in the following order:

```csv
memberId,firstName,lastName,email,phoneNumber,address,borrowedBook1Title|borrowDate1;borrowedBook2Title|borrowDate2.

