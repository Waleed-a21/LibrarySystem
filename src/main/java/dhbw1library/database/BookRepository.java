package dhbw1library.database;

import dhbw1library.db.tables.records.BooksRecord;
import org.jooq.DSLContext;
import org.jooq.Result;

// Statischer Import der generierten Tabelle (ermöglicht Zugriff via BOOKS statt Strings)
import static dhbw1library.db.Tables.BOOKS;

public class BookRepository {

    /**
     * Fügt ein neues Buch zur Datenbank hinzu.
     * @param title Titel des Buches
     * @param author Autor des Buches
     * @param isbn ISBN-Nummer
     */
    public void addBook(String title, String author, String isbn) {
        // Verbindung zur Datenbank holen
        DSLContext create = DatabaseManager.getContext();

        // Datensatz mit jOOQ einfügen (Type-Safe)
        create.insertInto(BOOKS)
                .set(BOOKS.TITLE, title)
                .set(BOOKS.AUTHOR, author)
                .set(BOOKS.ISBN, isbn)
                .set(BOOKS.PUBLISHED_YEAR, 2024) // Standardwert für Testzwecke
                .execute();

        System.out.println("✅ Buch erfolgreich hinzugefügt: " + title);
    }

    /**
     * Ruft alle Bücher aus der Datenbank ab und gibt sie auf der Konsole aus.
     */
    public void showAllBooks() {
        DSLContext create = DatabaseManager.getContext();

        // SELECT * FROM BOOKS
        Result<BooksRecord> result = create.selectFrom(BOOKS).fetch();

        System.out.println("=== Liste aller Bücher ===");
        if (result.isEmpty()) {
            System.out.println("(Keine Bücher vorhanden)");
        } else {
            for (BooksRecord book : result) {
                System.out.println("- " + book.getTitle() + " (Autor: " + book.getAuthor() + ")");
            }
        }
    }
}