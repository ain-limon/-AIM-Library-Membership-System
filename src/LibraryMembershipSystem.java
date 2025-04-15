import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LibraryMembershipSystem {

    private static final String DATA_FILE = "members.csv";

    public static void main(String[] args) {
        List<MemberInfo> members = loadMembersFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Library Membership Management System ---");
            System.out.println("1. Register New Member");
            System.out.println("2. View Member Information");
            System.out.println("3. Update Member Information");
            System.out.println("4. Delete Member");
            System.out.println("5. Display All Members");
            System.out.println("6. Find Member by Name");
            System.out.println("7. Save Data");
            System.out.println("8. Generate Report");
            System.out.println("9. Borrow Book");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewMember(members, scanner);
                    break;
                case "2":
                    viewMemberInfo(members, scanner);
                    break;
                case "3":
                    updateMemberInfo(members, scanner);
                    break;
                case "4":
                    deleteMember(members, scanner);
                    break;
                case "5":
                    displayAllMembers(members);
                    break;
                case "6":
                    findMemberByName(members, scanner);
                    break;
                case "7":
                    saveMembersToFile(members);
                    break;
                case "8":
                    generateReport(members);
                    break;
                case "9":
                    borrowBook(members, scanner);
                    break;
                case "10":
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static class MemberInfo {
        String memberId;
        String firstName;
        String lastName;
        String email;
        String phoneNumber;
        String address;
        List<BookBorrowed> borrowedBooks;

        public MemberInfo(String memberId, String firstName, String lastName, String email, String phoneNumber, String address) {
            this.memberId = memberId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.borrowedBooks = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(memberId)
                    .append(", Name: ").append(firstName).append(" ").append(lastName)
                    .append(", Email: ").append(email)
                    .append(", Phone: ").append(phoneNumber)
                    .append(", Address: ").append(address);

            if (!borrowedBooks.isEmpty()) {
                sb.append("\n  Borrowed Books:");
                for (BookBorrowed book : borrowedBooks) {
                    sb.append("\n    - ").append(book);
                }
            }

            return sb.toString();
        }

        public String toCsvString() {
            String borrowedBooksCsv = "";
            if (!borrowedBooks.isEmpty()) {
                List<String> bookStrings = new ArrayList<>();
                for (BookBorrowed book : borrowedBooks) {
                    bookStrings.add(book.toCsvString());
                }
                borrowedBooksCsv = String.join(";", bookStrings);
            }
            return String.format("%s,%s,%s,%s,%s,%s,%s", memberId, firstName, lastName, email, phoneNumber, address, borrowedBooksCsv);
        }

        public static MemberInfo fromCsvString(String csvRow) {
            String[] parts = csvRow.split(",");
            if (parts.length >= 6) {
                MemberInfo member = new MemberInfo(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                if (parts.length == 7 && !parts[6].isEmpty()) {
                    String[] borrowedBookStrings = parts[6].split(";");
                    for (String bookString : borrowedBookStrings) {
                        BookBorrowed book = BookBorrowed.fromCsvString(bookString);
                        if (book != null) {
                            member.borrowedBooks.add(book);
                        }
                    }
                }
                return member;
            }
            return null;
        }
    }

    static class BookBorrowed {
        String title;
        LocalDate borrowDate;

        public BookBorrowed(String title, LocalDate borrowDate) {
            this.title = title;
            this.borrowDate = borrowDate;
        }

        @Override
        public String toString() {
            return title + " (Borrowed on: " + borrowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ")";
        }

        public String toCsvString() {
            return String.format("%s|%s", title, borrowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        public static BookBorrowed fromCsvString(String csvString) {
            String[] parts = csvString.split("\\|");
            if (parts.length == 2) {
                return new BookBorrowed(parts[0], LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            return null;
        }
    }

    static void registerNewMember(List<MemberInfo> members, Scanner scanner) {
        System.out.println("\n--- Register New Member ---");

        String memberId;
        while (true) {
            System.out.print("Enter member ID (only digits): ");
            memberId = scanner.nextLine();
            if (isValidId(memberId)) {
                break;
            } else {
                System.out.println("Invalid ID. Please enter only digits.");
            }
        }

        String firstName;
        while (true) {
            System.out.print("Enter first name (only letters): ");
            firstName = scanner.nextLine();
            if (isValidName(firstName)) {
                break;
            } else {
                System.out.println("Invalid first name. Please enter only letters.");
            }
        }

        String lastName;
        while (true) {
            System.out.print("Enter last name (only letters): ");
            lastName = scanner.nextLine();
            if (isValidName(lastName)) {
                break;
            } else {
                System.out.println("Invalid last name. Please enter only letters.");
            }
        }

        String email;
        while (true) {
            System.out.print("Enter email (must end with @gmail.com): ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please ensure it ends with @gmail.com.");
            }
        }

        String phoneNumber;
        while (true) {
            System.out.print("Enter phone number (only digits): ");
            phoneNumber = scanner.nextLine();
            if (isValidPhone(phoneNumber)) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter only digits.");
            }
        }

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        MemberInfo newMember = new MemberInfo(memberId, firstName, lastName, email, phoneNumber, address);

        System.out.print("Borrow a book now? (yes/no): ");
        String borrowChoice = scanner.nextLine().toLowerCase();

        if (borrowChoice.equals("yes")) {
            System.out.print("Enter title of the book to borrow: ");
            String bookTitle = scanner.nextLine();
            if (!bookTitle.isEmpty()) {
                BookBorrowed bookBorrowed = new BookBorrowed(bookTitle, LocalDate.now());
                newMember.borrowedBooks.add(bookBorrowed);
                System.out.println("Book '" + bookTitle + "' added to borrowed list for " + firstName + " " + lastName + ".");
            }
        }

        members.add(newMember);
        System.out.println("Member " + firstName + " " + lastName + " registered successfully with ID: " + memberId);
    }

    static void viewMemberInfo(List<MemberInfo> members, Scanner scanner) {
        System.out.println("\n--- View Member Information ---");
        System.out.print("Enter member ID to view: ");
        String searchId = scanner.nextLine();

        for (MemberInfo member : members) {
            if (member.memberId.equals(searchId)) {
                System.out.println("Member Information:\n" + member);
                return;
            }
        }
        System.out.println("Member with ID " + searchId + " not found.");
    }

    static void updateMemberInfo(List<MemberInfo> members, Scanner scanner) {
        System.out.println("\n--- Update Member Information ---");
        System.out.print("Enter member ID to update: ");
        String updateId = scanner.nextLine();

        for (MemberInfo member : members) {
            if (member.memberId.equals(updateId)) {
                System.out.println("Current information for member:\n" + member);

                String newFirstName = member.firstName;
                while (true) {
                    System.out.print("Enter new first name (only letters, leave blank to keep): ");
                    String input = scanner.nextLine();
                    if (input.isEmpty() || isValidName(input)) {
                        newFirstName = input.isEmpty() ? newFirstName : input;
                        break;
                    } else {
                        System.out.println("Invalid first name. Please enter only letters.");
                    }
                }
                member.firstName = newFirstName;

                String newLastName = member.lastName;
                while (true) {
                    System.out.print("Enter new last name (only letters, leave blank to keep): ");
                    String input = scanner.nextLine();
                    if (input.isEmpty() || isValidName(input)) {
                        newLastName = input.isEmpty() ? newLastName : input;
                        break;
                    } else {
                        System.out.println("Invalid last name. Please enter only letters.");
                    }
                }
                member.lastName = newLastName;

                String newEmail = member.email;
                while (true) {
                    System.out.print("Enter new email (must end with @gmail.com, leave blank to keep): ");
                    String input = scanner.nextLine();
                    if (input.isEmpty() || isValidEmail(input)) {
                        newEmail = input.isEmpty() ? newEmail : input;
                        break;
                    } else {
                        System.out.println("Invalid email format. Please ensure it ends with @gmail.com.");
                    }
                }
                member.email = newEmail;

                String newPhoneNumber = member.phoneNumber;
                while (true) {
                    System.out.print("Enter new phone number (only digits, leave blank to keep): ");
                    String input = scanner.nextLine();
                    if (input.isEmpty() || isValidPhone(input)) {
                        newPhoneNumber = input.isEmpty() ? newPhoneNumber : input;
                        break;
                    } else {
                        System.out.println("Invalid phone number. Please enter only digits.");
                    }
                }
                member.phoneNumber = newPhoneNumber;

                System.out.print("Enter new address (leave blank to keep): ");
                String newAddress = scanner.nextLine();
                if (!newAddress.isEmpty()) member.address = newAddress;

                System.out.println("Member information with ID " + updateId + " updated successfully.");
                return;
            }
        }
        System.out.println("Member with ID " + updateId + " not found.");
    }

    static void deleteMember(List<MemberInfo> members, Scanner scanner) {
        System.out.println("\n--- Delete Member ---");
        System.out.print("Enter member ID to delete: ");
        String deleteId = scanner.nextLine();

        java.util.Iterator<MemberInfo> iterator = members.iterator();
        while (iterator.hasNext()) {
            MemberInfo member = iterator.next();
            if (member.memberId.equals(deleteId)) {
                iterator.remove();
                System.out.println("Member with ID " + deleteId + " deleted successfully.");
                return;
            }
        }
        System.out.println("Member with ID " + deleteId + " not found.");
    }

    static void displayAllMembers(List<MemberInfo> members) {
        System.out.println("\n--- List of All Members ---");
        if (members.isEmpty()) {
            System.out.println("No members registered in the library yet.");
            return;
        }
        for (MemberInfo member : members) {
            System.out.println(member);
        }
    }


}