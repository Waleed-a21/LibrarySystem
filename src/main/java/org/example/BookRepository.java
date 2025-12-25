package org.example;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.Field;

import static org.jooq.impl.DSL.*;

public class BookRepository {

    // تعريف الجدول والحقول يدويًا (jOOQ DSL)
    private static final Table<?> BOOKS = table("books");

    private static final Field<Integer> ID = field("id", Integer.class);
    private static final Field<String> TITLE = field("title", String.class);
    private static final Field<String> AUTHOR = field("author", String.class);
    private static final Field<String> ISBN = field("isbn", String.class);
    private static final Field<Integer> PUBLISHED_YEAR = field("published_year", Integer.class);

    public void addBook(String title, String author, String isbn) {
        DSLContext ctx = DatabaseManager.getContext();

        ctx.insertInto(BOOKS)
                .set(TITLE, title)
                .set(AUTHOR, author)
                .set(ISBN, isbn)
                .set(PUBLISHED_YEAR, 2024)
                .execute();
    }

    public void showAllBooks() {
        DSLContext ctx = DatabaseManager.getContext();

        Result<Record> result = ctx.select()
                .from(BOOKS)
                .fetch();

        System.out.println("=== Bücher ===");
        result.forEach(r ->
                System.out.println(
                        r.get(TITLE) + " | " + r.get(AUTHOR)
                )
        );
    }
}
