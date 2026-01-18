package org.example;

import org.example.db.Tables;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    private DSLContext ctx;
    private BookRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");
        ctx = DSL.using(conn, SQLDialect.SQLITE);
        repository = new BookRepository(ctx);

        // Initialize DB to ensure tables exist
        try {
            DatabaseManager.initializeDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // تنظيف الجدول قبل كل اختبار
        ctx.deleteFrom(Tables.BOOKS).execute();
    }

    @Test
    void testAddBook() {
        // Act
        int bookId = repository.addBook(
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                5);

        // Assert
        assertTrue(bookId > 0, "Returned Book ID should be positive");

        var result = ctx.selectFrom(Tables.BOOKS).fetch();

        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
        assertEquals(5, result.get(0).getTotalCopies());
        assertEquals(5, result.get(0).getAvailableCopies());
        assertEquals(bookId, result.get(0).getId(), "ID in DB should match returned ID");
    }
}
