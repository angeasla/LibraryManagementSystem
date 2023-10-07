package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBookService {
    Long getTotalCount();
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id) throws EntityNotFoundException;
    List<Book> getByIds(List<Long> ids);
    List<BookDTO> getBooksByTitle(String title);
    BookDTO createBook(BookDTO bookDTO) throws EntityNotFoundException;
    BookDTO updateBook(Long bookId, BookDTO bookDTO) throws EntityNotFoundException;
    Book save(Book book);
    void deleteBook(Long bookId) throws EntityNotFoundException;
    Book getBookByIdToDelete(Long bookId) throws EntityNotFoundException;
    Book updateBookQuantity(Book book);
    List<Book> findBooksByPublisherId(Long publisherId);
    List<Book> findBooksByAuthorId(Long authorId);
    Page<BookDTO> getAllBooksWithPagination(int page, int size);
}
