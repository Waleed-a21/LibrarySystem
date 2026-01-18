package org.example;

import org.jooq.DSLContext;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("📚 Library System starting...");
        System.out.println("=================================");

        try {
            // 1️⃣ Initialize Database (Safe init, no dropping tables)
            DatabaseManager.initializeDatabase();

            // 2️⃣ Connection
            DSLContext ctx = DatabaseManager.getDSLContext();
            System.out.println("✅ Database connection established");

            // 3️⃣ Repositories
            BookRepository bookRepo = new BookRepository(ctx);
            MemberRepository memberRepo = new MemberRepository(ctx);
            LoanRepository loanRepo = new LoanRepository(ctx);

            // 4️⃣ Demo Interaction
            // Try to add a book and get its ID (handling potential duplicates if needed)
            int bookId;
            try {
                bookId = bookRepo.addBook("Clean Code", "Robert C. Martin", "978-0132350884", 3);
                System.out.println("📖 Book added with ID: " + bookId);
            } catch (Exception e) {
                // If ISBN exists (Unique constraint), just fetch the existing ID
                System.out.println("⚠️ Book might already exist. Fetching ID...");
                var bookRecord = ctx.selectFrom(org.example.db.Tables.BOOKS)
                        .where(org.example.db.Tables.BOOKS.ISBN.eq("978-0132350884"))
                        .fetchOne();

                if (bookRecord != null) {
                    bookId = bookRecord.getId();
                    System.out.println("📖 Found existing Book ID: " + bookId);
                } else {
                    throw new RuntimeException("Could not add or find book.");
                }
            }

            // Ensure we have a member
            // Ensure we have a member
            int memberId;
            try {
                memberId = memberRepo.addMember("Max Mustermann", "max@uni.de");
                System.out.println("👤 Member added with ID: " + memberId);
            } catch (Exception e) {
                System.out.println("⚠️ Member might already exist. Fetching ID...");
                var memberRecord = ctx.selectFrom(org.example.db.Tables.MEMBERS)
                        .where(org.example.db.Tables.MEMBERS.EMAIL.eq("max@uni.de"))
                        .fetchOne();

                if (memberRecord != null) {
                    memberId = memberRecord.getId();
                    System.out.println("👤 Found existing Member ID: " + memberId);
                } else {
                    throw new RuntimeException("Could not add or find member.");
                }
            }

            // Checkout
            try {
                loanRepo.checkoutBook(bookId, memberId);
                System.out.println("✅ Book checked out successfully");
            } catch (Exception e) {
                System.out.println("ℹ️ Checkout info: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("❌ Error while running application:");
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }

        System.out.println("=================================");
        System.out.println("🏁 Library System finished");
        System.out.println("=================================");
    }
}