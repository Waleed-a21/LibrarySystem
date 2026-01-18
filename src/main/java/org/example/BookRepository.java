package org.example;

import org.example.db.Tables;
import org.example.db.tables.records.BooksRecord;
import org.jooq.DSLContext;

public class BookRepository {

    private final DSLContext ctx;

    public BookRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Adds a new book to the database.
     *
     * @return The ID of the newly created book.
     */
    public int addBook(String title, String author, String isbn, int totalCopies) {
        return ctx.insertInto(Tables.BOOKS)
                .set(Tables.BOOKS.TITLE, title)
                .set(Tables.BOOKS.AUTHOR, author)
                .set(Tables.BOOKS.ISBN, isbn)
                .set(Tables.BOOKS.TOTAL_COPIES, totalCopies)
                .set(Tables.BOOKS.AVAILABLE_COPIES, totalCopies)
                .returningResult(Tables.BOOKS.ID)
                .fetchOne()
                .getValue(Tables.BOOKS.ID);
    }

    /**
     * Finds a book by its ISBN.
     *
     * @param isbn The ISBN to search for.
     * @return The BooksRecord if found, or null if not found.
     */
    public BooksRecord findBookByIsbn(String isbn) {
        return ctx.selectFrom(Tables.BOOKS)
                .where(Tables.BOOKS.ISBN.eq(isbn))
                .fetchOne();
    }

    /**
     * Finds a book by its ID.
     *
     * @param id The ID to search for.
     * @return The BooksRecord if found, or null if not found.
     */
    public BooksRecord findBookById(int id) {
        return ctx.selectFrom(Tables.BOOKS)
                .where(Tables.BOOKS.ID.eq(id))
                .fetchOne();
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @return true if a book was deleted, false otherwise.
     */
    public boolean deleteBook(int id) {
        int rowsAffected = ctx.deleteFrom(Tables.BOOKS)
                .where(Tables.BOOKS.ID.eq(id))
                .execute();
        return rowsAffected > 0;
    }

    /**
     * Deletes a book by its ISBN.
     *
     * @param isbn The ISBN of the book to delete.
     * @return true if a book was deleted, false otherwise.
     */
    public boolean deleteBookByIsbn(String isbn) {
        int rowsAffected = ctx.deleteFrom(Tables.BOOKS)
                .where(Tables.BOOKS.ISBN.eq(isbn))
                .execute();
        return rowsAffected > 0;
    }
}