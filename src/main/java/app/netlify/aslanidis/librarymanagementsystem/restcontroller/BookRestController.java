package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.service.IBookService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import app.netlify.aslanidis.librarymanagementsystem.service.utilities.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final IBookService bookService;

    @Autowired
    public BookRestController(IBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO) throws EntityNotFoundException {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookDTO bookDTO) throws EntityNotFoundException {
        BookDTO updatedBook = bookService.updateBook(bookId, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) throws EntityNotFoundException {
        bookService.deleteBook(bookId);
    }

    @GetMapping("/{bookId}")
    public BookDTO getBookById(@PathVariable("bookId") Long bookId) throws EntityNotFoundException {
        return bookService.getBookById(bookId);
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<BookDTO>> getAllBooks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<BookDTO> books = bookService.getAllBooksWithPagination(page, size);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search-by-title/{title}")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@PathVariable String title) {
        List<BookDTO> books = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/count/total")
    public Long getTotalCount() {
        return bookService.getTotalCount();
    }

    @GetMapping("/by-publisher/{publisherId}")
    public List<BookDTO> getBooksByPublisher(@PathVariable Long publisherId) {
        List<Book> books = bookService.findBooksByPublisherId(publisherId);
        return books.stream().map(DTOConverter::convertBookToDTO).collect(Collectors.toList());
    }

    @GetMapping("/by-author/{authorId}")
    public List<BookDTO> getBooksByAuthor(@PathVariable Long authorId) {
        List<Book> books = bookService.findBooksByAuthorId(authorId);
        return books.stream().map(DTOConverter::convertBookToDTO).collect(Collectors.toList());
    }
}
