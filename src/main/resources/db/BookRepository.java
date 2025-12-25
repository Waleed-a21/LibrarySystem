package db;

import org.jooq.DSLContext;
import org.jooq.Result;

import dhbw1library.db.tables.records.BooksRecord;

import static dhbw1library.db.Tables.BOOKS;

public class BookRepository {

    /**
     * Fügt ein neues Buch in die Datenbank ein.
     */
    public void addBook(String title,
                        String author,
                        String isbn,
                        int publishedYear,
                        int totalCopies) {

        DSLContext ctx = DatabaseManager.getContext();

        ctx.insertInto(BOOKS)
                .set(BOOKS.TITLE, title)
                .set(BOOKS.AUTHOR, author)
                .set(BOOKS.ISBN, isbn)
                .set(BOOKS.PUBLISHED_YEAR, publishedYear)
                .set(BOOKS.TOTAL_COPIES, totalCopies)
                .set(BOOKS.AVAILABLE_COPIES, totalCopies)
                .execute();

        System.out.println("Buch hinzugefügt: " + title);
    }

    /**
     * Gibt alle Bücher aus der Datenbank zurück.
     */
    public Result<BooksRecord> getAllBooks() {
        DSLContext ctx = DatabaseManager.getContext();
        return ctx.selectFrom(BOOKS).fetch();
    }

    /**
     * Gibt alle Bücher auf der Konsole aus (Testzwecke).
     */
    public void printAllBooks() {
        Result<BooksRecord> books = getAllBooks();

        System.out.println("=== Bücherliste ===");
        if (books.isEmpty()) {
            System.out.println("Keine Bücher vorhanden.");
        } else {
            for (BooksRecord book : books) {
                System.out.println(
                        book.getId() + " | " +
                                book.getTitle() + " | " +
                                book.getAuthor() + " | verfügbar: " +
                                book.getAvailableCopies()
                );
            }
        }
    }

    /**
     * Reduziert die verfügbaren Exemplare eines Buches (Ausleihe).
     */
    public void decreaseAvailableCopies(int bookId) {
        DSLContext ctx = DatabaseManager.getContext();

        ctx.update(BOOKS)
                .set(BOOKS.AVAILABLE_COPIES, BOOKS.AVAILABLE_COPIES.minus(1))
                .where(BOOKS.ID.eq(bookId))
                .and(BOOKS.AVAILABLE_COPIES.gt(0))
                .execute();
    }

    /**
     * Erhöht die verfügbaren Exemplare eines Buches (Rückgabe).
     */
    public void increaseAvailableCopies(int bookId) {
        DSLContext ctx = DatabaseManager.getContext();

        ctx.update(BOOKS)
                .set(BOOKS.AVAILABLE_COPIES, BOOKS.AVAILABLE_COPIES.plus(1))
                .where(BOOKS.ID.eq(bookId))
                .execute();
    }
}
