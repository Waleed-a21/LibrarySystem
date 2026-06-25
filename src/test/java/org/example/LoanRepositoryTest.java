package org.example;

import org.example.db.Tables;
import org.example.repository.BookRepository;
import org.example.repository.DatabaseManager;
import org.example.repository.LoanRepository;
import org.example.repository.MemberRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoanRepositoryTest {

    private DSLContext ctx;
    private LoanRepository loanRepo;
    private BookRepository bookRepo;
    private MemberRepository memberRepo;

    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");
        ctx = DSL.using(conn, SQLDialect.SQLITE);
        loanRepo = new LoanRepository(ctx);
        bookRepo = new BookRepository(ctx);
        memberRepo = new MemberRepository(ctx);

        // Initialize DB
        try {
            DatabaseManager.initializeDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Alles leeren (Reihenfolge wegen Foreign Keys wichtig!)
        ctx.deleteFrom(Tables.LOANS).execute();
        ctx.deleteFrom(Tables.BOOKS).execute();
        ctx.deleteFrom(Tables.MEMBERS).execute();
    }

    @Test
    void testCheckoutBookSuccessfully() {
        // Arrange
        bookRepo.addBook("Junit 5", "Author", "111", 1);
        memberRepo.addMember("Test User", "test@mail.com");

        int bookId = ctx.selectFrom(Tables.BOOKS).fetchOne().getId();
        int memberId = ctx.selectFrom(Tables.MEMBERS).fetchOne().getId();

        // Act
        loanRepo.checkoutBook(bookId, memberId);

        // Assert: Loan existiert & Kopien reduziert
        boolean loanExists = ctx.fetchExists(
                ctx.selectFrom(Tables.LOANS).where(Tables.LOANS.BOOK_ID.eq(bookId)));
        assertTrue(loanExists);

        int available = ctx.select(Tables.BOOKS.AVAILABLE_COPIES)
                .from(Tables.BOOKS)
                .where(Tables.BOOKS.ID.eq(bookId))
                .fetchOneInto(Integer.class);
        assertEquals(0, available);
    }

    @Test
    void testCheckoutFailsIfNoCopiesAvailable() {
        // Arrange: Buch mit 0 Kopien
        bookRepo.addBook("Rare Book", "Author", "222", 0);
        memberRepo.addMember("Test User", "test@mail.com");

        int bookId = ctx.selectFrom(Tables.BOOKS).fetchOne().getId();
        int memberId = ctx.selectFrom(Tables.MEMBERS).fetchOne().getId();

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            loanRepo.checkoutBook(bookId, memberId);
        });

        assertEquals("No copies available", exception.getMessage());
    }

    @Test
    void testReturnBook() {
        // Arrange: Buch ausleihen
        bookRepo.addBook("Return Me", "Author", "333", 1);
        memberRepo.addMember("Test User", "test@mail.com");

        int bookId = ctx.selectFrom(Tables.BOOKS).fetchOne().getId();
        int memberId = ctx.selectFrom(Tables.MEMBERS).fetchOne().getId();

        loanRepo.checkoutBook(bookId, memberId); // Jetzt 0 Kopien verfügbar
        int loanId = ctx.selectFrom(Tables.LOANS).fetchOne().getId();

        // Act: Zurückgeben
        loanRepo.returnBook(loanId);

        // Assert: Kopien wieder 1 & Rückgabedatum gesetzt
        int available = ctx.select(Tables.BOOKS.AVAILABLE_COPIES)
                .from(Tables.BOOKS)
                .where(Tables.BOOKS.ID.eq(bookId))
                .fetchOneInto(Integer.class);
        assertEquals(1, available, "Book copies should increase after return");

        var loan = ctx.selectFrom(Tables.LOANS).where(Tables.LOANS.ID.eq(loanId)).fetchOne();
        assertNotNull(loan.getReturnDate(), "Return date should be set");
    }
}