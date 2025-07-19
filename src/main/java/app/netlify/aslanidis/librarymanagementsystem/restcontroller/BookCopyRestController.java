package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;
import app.netlify.aslanidis.librarymanagementsystem.service.IBookCopyService;
import app.netlify.aslanidis.librarymanagementsystem.service.IBookService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book-copies")
public class BookCopyRestController {

    private final IBookCopyService bookCopyService;
    private final IBookService bookService;

    @Autowired
    public BookCopyRestController(IBookCopyService bookCopyService, IBookService bookService) {
        this.bookCopyService = bookCopyService;
        this.bookService = bookService;
    }

    // Get all copies of a specific book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookCopy>> getBookCopiesByBook(@PathVariable Long bookId) throws EntityNotFoundException {
        Book book = bookService.getBookByIdToDelete(bookId);
        List<BookCopy> copies = bookCopyService.getBookCopiesByBook(book);
        return new ResponseEntity<>(copies, HttpStatus.OK);
    }

    // Get available copies of a specific book
    @GetMapping("/book/{bookId}/available")
    public ResponseEntity<List<BookCopy>> getAvailableBookCopiesByBook(@PathVariable Long bookId) throws EntityNotFoundException {
        Book book = bookService.getBookByIdToDelete(bookId);
        List<BookCopy> availableCopies = bookCopyService.getAvailableBookCopiesByBook(book);
        return new ResponseEntity<>(availableCopies, HttpStatus.OK);
    }

    // Get available copies count for a specific book
    @GetMapping("/book/{bookId}/available/count")
    public ResponseEntity<Integer> getAvailableCopiesCount(@PathVariable Long bookId) throws EntityNotFoundException {
        Book book = bookService.getBookByIdToDelete(bookId);
        Integer count = bookCopyService.getAvailableCopiesCount(book);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Get total copies count for a specific book
    @GetMapping("/book/{bookId}/total/count")
    public ResponseEntity<Integer> getTotalCopiesCount(@PathVariable Long bookId) throws EntityNotFoundException {
        Book book = bookService.getBookByIdToDelete(bookId);
        Integer count = bookCopyService.getTotalCopiesCount(book);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Get a specific book copy by ID
    @GetMapping("/{copyId}")
    public ResponseEntity<BookCopy> getBookCopyById(@PathVariable Long copyId) {
        Optional<BookCopy> bookCopy = bookCopyService.getBookCopyById(copyId);
        return bookCopy.map(copy -> new ResponseEntity<>(copy, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add copies to a book
    @PostMapping("/book/{bookId}/add/{quantity}")
    public ResponseEntity<List<BookCopy>> addBookCopies(@PathVariable Long bookId, @PathVariable Integer quantity) throws EntityNotFoundException {
        Book book = bookService.getBookByIdToDelete(bookId);
        List<BookCopy> newCopies = bookCopyService.createMultipleBookCopies(book, quantity);
        return new ResponseEntity<>(newCopies, HttpStatus.CREATED);
    }

    // Update a book copy (e.g., condition)
    @PutMapping("/{copyId}")
    public ResponseEntity<BookCopy> updateBookCopy(@PathVariable Long copyId, @RequestBody BookCopy bookCopyUpdate) {
        Optional<BookCopy> existingCopy = bookCopyService.getBookCopyById(copyId);
        if (!existingCopy.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookCopy copy = existingCopy.get();
        if (bookCopyUpdate.getCondition() != null) {
            copy.setCondition(bookCopyUpdate.getCondition());
        }
        
        BookCopy updatedCopy = bookCopyService.updateBookCopy(copy);
        return new ResponseEntity<>(updatedCopy, HttpStatus.OK);
    }

    // Delete a book copy
    @DeleteMapping("/{copyId}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable Long copyId) {
        Optional<BookCopy> bookCopy = bookCopyService.getBookCopyById(copyId);
        if (!bookCopy.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if the copy is currently borrowed
        if (!bookCopy.get().getIsAvailable()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Cannot delete borrowed copy
        }

        bookCopyService.deleteBookCopy(copyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}