package org.example;

import org.example.db.tables.records.BooksRecord;
import org.jooq.DSLContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("       📚 LIBRARY SYSTEM v2.0 📚");
        System.out.println("===========================================");

        Scanner scanner = new Scanner(System.in);

        try {
            // 1️⃣ Initialize Database
            DatabaseManager.initializeDatabase();

            // 2️⃣ Get Connection
            DSLContext ctx = DatabaseManager.getDSLContext();
            System.out.println("✅ Database connection established.\n");

            // 3️⃣ Initialize Repositories
            BookRepository bookRepo = new BookRepository(ctx);
            // MemberRepository and LoanRepository can be added here for future menu options

            // 4️⃣ Main Menu Loop
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
                    case 1 -> addBook(scanner, bookRepo);
                    case 2 -> searchBook(scanner, bookRepo);
                    case 3 -> deleteBook(scanner, bookRepo);
                    case 4 -> {
                        running = false;
                        System.out.println("\n👋 Goodbye! Thank you for using Library System.");
                    }
                    default -> System.out.println("\n⚠️ Invalid option. Please choose 1-4.\n");
                }
            }

        } catch (Exception e) {
            System.err.println("❌ A critical error occurred:");
            e.printStackTrace();
        } finally {
            scanner.close();
            DatabaseManager.closeConnection();
        }

        System.out.println("===========================================");
        System.out.println("           🏁 Application Closed");
        System.out.println("===========================================");
    }

    /**
     * Prints the main menu options.
     */
    private static void printMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("                 MAIN MENU");
        System.out.println("-------------------------------------------");
        System.out.println("  1. ➕ Add a Book");
        System.out.println("  2. 🔍 Search for a Book (by ISBN)");
        System.out.println("  3. 🗑️  Delete a Book (by ID)");
        System.out.println("  4. 🚪 Exit");
        System.out.println("-------------------------------------------");
    }

    /**
     * Handles the "Add a Book" workflow.
     */
    private static void addBook(Scanner scanner, BookRepository bookRepo) {
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
            int newBookId = bookRepo.addBook(title, author, isbn, copies);
            System.out.println("\n✅ Book added successfully! ID: " + newBookId + "\n");
        } catch (Exception e) {
            // Likely a UNIQUE constraint violation on ISBN
            System.out.println("\n❌ Error: Could not add book. ISBN might already exist.\n");
        }
    }

    /**
     * Handles the "Search for a Book" workflow.
     */
    private static void searchBook(Scanner scanner, BookRepository bookRepo) {
        System.out.println("\n--- Search for a Book ---");
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine();

        BooksRecord book = bookRepo.findBookByIsbn(isbn);

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

    /**
     * Handles the "Delete a Book" workflow.
     */
    private static void deleteBook(Scanner scanner, BookRepository bookRepo) {
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

        // Optional: Show book details before deleting
        BooksRecord book = bookRepo.findBookById(bookId);
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
            boolean deleted = bookRepo.deleteBook(bookId);
            if (deleted) {
                System.out.println("\n✅ Book deleted successfully!\n");
            } else {
                System.out.println("\n❌ Could not delete the book.\n");
            }
        } else {
            System.out.println("\n🔙 Deletion cancelled.\n");
        }
    }
}