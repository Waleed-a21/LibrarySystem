package org.example.service;

import org.example.repository.BookRepository;
import org.example.repository.LoanRepository;
import org.example.repository.MemberRepository;
import org.example.db.tables.records.BooksRecord;

public class LibraryService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;

    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
    }

    public int addBook(String title, String author, String isbn, int copies) {
        return bookRepository.addBook(title, author, isbn, copies);
    }

    public BooksRecord searchBook(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public BooksRecord getBookById(int id) {
        return bookRepository.findBookById(id);
    }

    public boolean deleteBook(int id) {
        return bookRepository.deleteBook(id);
    }

    public int addMember(String name, String email) {
        return memberRepository.addMember(name, email);
    }

    public void checkoutBook(int bookId, int memberId) {
        loanRepository.checkoutBook(bookId, memberId);
    }

    public void returnBook(int loanId) {
        loanRepository.returnBook(loanId);
    }
}
