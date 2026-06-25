package org.example.repository;

import org.example.db.Tables;
import org.example.db.tables.records.BooksRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.time.LocalDate;

public class LoanRepository {
    private final DSLContext ctx;

    public LoanRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    // Buch ausleihen (Transactional!)
    public void checkoutBook(int bookId, int memberId) {
        ctx.transaction(configuration -> {
            DSLContext tx = DSL.using(configuration);

            // 1. Prüfen ob Buch existiert und verfügbar ist
            BooksRecord book = tx.selectFrom(Tables.BOOKS)
                    .where(Tables.BOOKS.ID.eq(bookId))
                    .fetchOne();

            if (book == null) {
                throw new RuntimeException("Book not found");
            }

            if (book.getAvailableCopies() <= 0) {
                throw new RuntimeException("No copies available");
            }

            // 2. Verfügbarkeit verringern
            tx.update(Tables.BOOKS)
                    .set(Tables.BOOKS.AVAILABLE_COPIES, book.getAvailableCopies() - 1)
                    .where(Tables.BOOKS.ID.eq(bookId))
                    .execute();

            // 3. Eintrag in Loans Tabelle
            tx.insertInto(Tables.LOANS)
                    .set(Tables.LOANS.BOOK_ID, bookId)
                    .set(Tables.LOANS.MEMBER_ID, memberId)
                    .set(Tables.LOANS.LOAN_DATE, LocalDate.now().toString())
                    .set(Tables.LOANS.DUE_DATE, LocalDate.now().plusWeeks(2).toString())
                    .execute();
        });
    }

    // Buch zurückgeben
    public void returnBook(int loanId) {
        ctx.transaction(configuration -> {
            DSLContext tx = DSL.using(configuration);

            // 1. Loan ID suchen um Book ID zu bekommen
            var loan = tx.selectFrom(Tables.LOANS)
                    .where(Tables.LOANS.ID.eq(loanId))
                    .fetchOne();

            if (loan == null || loan.getReturnDate() != null) {
                throw new RuntimeException("Loan not found or already returned");
            }

            // 2. Rückgabedatum setzen
            tx.update(Tables.LOANS)
                    .set(Tables.LOANS.RETURN_DATE, LocalDate.now().toString())
                    .where(Tables.LOANS.ID.eq(loanId))
                    .execute();

            // 3. Verfügbarkeit des Buches wieder erhöhen
            tx.update(Tables.BOOKS)
                    .set(Tables.BOOKS.AVAILABLE_COPIES, Tables.BOOKS.AVAILABLE_COPIES.plus(1))
                    .where(Tables.BOOKS.ID.eq(loan.getBookId()))
                    .execute();
        });
    }
}