package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IBookService {
    Long getTotalCount();
    Long getTotalBorrowedBooks();
    List<Book> getAllBooks();
    Book getBookById(Long id);
    List<Book> getByIds(List<Long> ids);
    List<Book> getBooksByTitle(String title);
    Book createBook(Book book);
    Book updateBook(Long bookId, Book updatedBook);
    Book save(Book book);
    void deleteBook(Long bookId);
    boolean hasUsage(Book book);
}
