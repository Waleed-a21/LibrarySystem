package org.example;

import org.example.db.Tables;
import org.jooq.DSLContext;

public class BookRepository {

  private final DSLContext ctx;

  public BookRepository(DSLContext ctx) {
    this.ctx = ctx;
  }

  // Changed return type to int to return the ID of the new book
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
}
