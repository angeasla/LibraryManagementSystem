package app.netlify.aslanidis.librarymanagementsystem.service;


import app.netlify.aslanidis.librarymanagementsystem.common.Constants;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IBorrowedBookService borrowedBookService;

    @Override
    public Long getTotalCount() {
        return bookRepository.count();
    }

    @Override
    public Long getTotalBorrowedBooks() {
        return bookRepository.countByStatus(Constants.BOOK_BORROWED);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> getByIds(List<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findBookByTitleContainingIgnoreCase(title);
    }

    @Override
    public Book createBook(Book book) {
        book.setStatus(Constants.BOOK_AVAILABLE);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book updatedBook) {
        Book book = getBookById(bookId);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        book.setPublisher(updatedBook.getPublisher());
        book.setPages(updatedBook.getPages());
        book.setStatus(Constants.BOOK_AVAILABLE);
        return bookRepository.save(book);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = getBookById(bookId);
        bookRepository.delete(book);
    }

    @Override
    public boolean hasUsage(Book book) {
        return borrowedBookService.getCountByBook(book) > 0;
    }


    // Helper method to perform validation using the BookValidator
    /*private void validateBook(BookDTO bookDTO) {
        Errors errors = new BeanPropertyBindingResult(bookDTO, "book");
        bookValidator.validate(bookDTO, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid book data");
        }
    }*/
}
