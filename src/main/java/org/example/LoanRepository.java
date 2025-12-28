package org.example;

import org.jooq.DSLContext;
// buch ausleihen
public class LoanRepository {
    private final DSLContext ctx;

    public LoanRepository(DSLContext ctx) {
        this.ctx = ctx;
    }
    //buch nehmen
    public void CheckoutBook(int bookID, int memberID){

    }
    //buch zurückgeben
    public void ReturnBook(int bookID){

    }
}
