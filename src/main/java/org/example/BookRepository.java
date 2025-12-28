package org.example;


import org.jooq.DSLContext;


public class BookRepository {
  private final DSLContext ctx;

  public BookRepository (DSLContext ctx){
      this.ctx = ctx;
  }

  public  void AddBook(String title, String author, String isbn, int totalCopies ){

      // to do
  }
    }
