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

   }