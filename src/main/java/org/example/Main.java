package org.example;

import org.example.repository.BookRepository;
import org.example.repository.DatabaseManager;
import org.example.repository.LoanRepository;
import org.example.repository.MemberRepository;
import org.example.service.LibraryService;
import org.example.ui.ConsoleUI;
import org.jooq.DSLContext;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // 1️⃣ Initialize Database
            DatabaseManager.initializeDatabase();

            // 2️⃣ Get Connection
            DSLContext ctx = DatabaseManager.getDSLContext();
            System.out.println("✅ Database connection established.\n");

            // 3️⃣ Initialize Repositories
            BookRepository bookRepo = new BookRepository(ctx);
            LoanRepository loanRepo = new LoanRepository(ctx);
            MemberRepository memberRepo = new MemberRepository(ctx);

            // 4️⃣ Initialize Services
            LibraryService libraryService = new LibraryService(bookRepo, loanRepo, memberRepo);

            // 5️⃣ Initialize and Start UI
            ConsoleUI consoleUI = new ConsoleUI(libraryService, scanner);
            consoleUI.start();

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
}