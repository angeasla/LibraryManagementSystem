package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;

import java.util.List;
import java.util.Optional;

public interface IBookCopyService {
    
    BookCopy createBookCopy(Book book, Integer copyNumber);
    List<BookCopy> createMultipleBookCopies(Book book, Integer quantity);
    Optional<BookCopy> getBookCopyById(Long copyId);
    List<BookCopy> getBookCopiesByBook(Book book);
    List<BookCopy> getAvailableBookCopiesByBook(Book book);
    Integer getAvailableCopiesCount(Book book);
    Integer getTotalCopiesCount(Book book);
    BookCopy updateBookCopy(BookCopy bookCopy);
    void deleteBookCopy(Long copyId);
}