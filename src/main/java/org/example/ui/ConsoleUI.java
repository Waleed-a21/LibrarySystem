package org.example.ui;

import org.example.service.LibraryService;
import org.example.db.tables.records.BooksRecord;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {
    private final LibraryService libraryService;
    private final Scanner scanner;

    public ConsoleUI(LibraryService libraryService, Scanner scanner) {
        this.libraryService = libraryService;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("===========================================");
        System.out.println("       📚 LIBRARY SYSTEM v2.0 📚");
        System.out.println("===========================================");

        boolean running = true;
        while (running) {
            printMenu();

            int choice = -1;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline
            } catch (InputMismatchException e) {
                System.out.println("\n❌ Invalid input! Please enter a number.\n");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1 -> addBook();
                case 2 -> searchBook();
                case 3 -> deleteBook();
                case 4 -> addMember();
                case 5 -> checkoutBook();
                case 6 -> returnBook();
                case 7 -> {
                    running = false;
                    System.out.println("\n👋 Goodbye! Thank you for using Library System.");
                }
                default -> System.out.println("\n⚠️ Invalid option. Please choose 1-7.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("                 MAIN MENU");
        System.out.println("-------------------------------------------");
        System.out.println("  1. ➕ Add a Book");
        System.out.println("  2. 🔍 Search for a Book (by ISBN)");
        System.out.println("  3. 🗑️  Delete a Book (by ID)");
        System.out.println("  4. 👤 Add a Member");
        System.out.println("  5. 📤 Checkout a Book");
        System.out.println("  6. 📥 Return a Book");
        System.out.println("  7. 🚪 Exit");
        System.out.println("-------------------------------------------");
    }

    private void addBook() {
        System.out.println("\n--- Add a New Book ---");

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        int copies = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter Number of Copies: ");
            try {
                copies = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (copies < 0) {
                    System.out.println("❌ Copies cannot be negative. Try again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        try {
            int newBookId = libraryService.addBook(title, author, isbn, copies);
            System.out.println("\n✅ Book added successfully! ID: " + newBookId + "\n");
        } catch (Exception e) {
            System.out.println("\n❌ Error: Could not add book. ISBN might already exist.\n");
        }
    }

    private void searchBook() {
        System.out.println("\n--- Search for a Book ---");
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine();

        BooksRecord book = libraryService.searchBook(isbn);

        if (book != null) {
            System.out.println("\n📖 Book Found:");
            System.out.println("   ID:              " + book.getId());
            System.out.println("   Title:           " + book.getTitle());
            System.out.println("   Author:          " + book.getAuthor());
            System.out.println("   ISBN:            " + book.getIsbn());
            System.out.println("   Total Copies:    " + book.getTotalCopies());
            System.out.println("   Available:       " + book.getAvailableCopies());
            System.out.println();
        } else {
            System.out.println("\n❌ No book found with ISBN: " + isbn + "\n");
        }
    }

    private void deleteBook() {
        System.out.println("\n--- Delete a Book ---");

        int bookId = -1;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter Book ID to delete: ");
            try {
                bookId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a numeric ID.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        BooksRecord book = libraryService.getBookById(bookId);
        if (book == null) {
            System.out.println("\n❌ No book found with ID: " + bookId + "\n");
            return;
        }

        System.out.println("\n⚠️  You are about to delete:");
        System.out.println("   Title:  " + book.getTitle());
        System.out.println("   Author: " + book.getAuthor());
        System.out.println("   ISBN:   " + book.getIsbn());
        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            boolean deleted = libraryService.deleteBook(bookId);
            if (deleted) {
                System.out.println("\n✅ Book deleted successfully!\n");
            } else {
                System.out.println("\n❌ Could not delete the book.\n");
            }
        } else {
            System.out.println("\n🔙 Deletion cancelled.\n");
        }
    }

    private void addMember() {
        System.out.println("\n--- Add a New Member ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        try {
            int newMemberId = libraryService.addMember(name, email);
            System.out.println("\n✅ Member added successfully! ID: " + newMemberId + "\n");
        } catch (Exception e) {
            System.out.println("\n❌ Error: Could not add member. Email might already exist.\n");
        }
    }

    private void checkoutBook() {
        System.out.println("\n--- Checkout a Book ---");

        System.out.print("Enter Book ID: ");
        int bookId = -1;
        try {
            bookId = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a numeric Book ID.");
            scanner.nextLine();
            return;
        }

        System.out.print("Enter Member ID: ");
        int memberId = -1;
        try {
            memberId = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a numeric Member ID.");
            scanner.nextLine();
            return;
        }

        try {
            libraryService.checkoutBook(bookId, memberId);
            System.out.println("\n✅ Book checked out successfully!\n");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage() + "\n");
        }
    }

    private void returnBook() {
        System.out.println("\n--- Return a Book ---");

        System.out.print("Enter Loan ID: ");
        int loanId = -1;
        try {
            loanId = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a numeric Loan ID.");
            scanner.nextLine();
            return;
        }

        try {
            libraryService.returnBook(loanId);
            System.out.println("\n✅ Book returned successfully!\n");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage() + "\n");
        }
    }
}
