package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.dto.BorrowRequest;
import app.netlify.aslanidis.librarymanagementsystem.dto.ReturnRequest;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.service.IBookService;
import app.netlify.aslanidis.librarymanagementsystem.service.IBorrowService;
import app.netlify.aslanidis.librarymanagementsystem.service.IUserService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrows")
public class BorrowRestController {

    private final IUserService userService;
    private final IBookService bookService;
    private final IBorrowService borrowService;

    @Autowired
    public BorrowRestController(IUserService userService,
                                IBookService bookService,
                                IBorrowService borrowService) {
        this.userService = userService;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    // Borrow a book to a user
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowRequest borrowRequest) {

            Long userId = borrowRequest.getUserId();
            Long bookId = borrowRequest.getBookId();

            Optional<Borrow> optionalBorrow = borrowService.borrowBook(userId, bookId);

            if (!optionalBorrow.isPresent()) {
                return new ResponseEntity<>("Book is already borrowed", HttpStatus.BAD_REQUEST);
            }

            Borrow borrow = optionalBorrow.get();
            return new ResponseEntity<>(borrow, HttpStatus.CREATED);
    }

    // Return a book from a user
    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody ReturnRequest returnRequest) throws EntityNotFoundException {
        Long userId = returnRequest.getUserId();
        Long bookId = returnRequest.getBookId();

        Optional<Borrow> optionalReturn = borrowService.returnBook(userId, bookId);

        if (!optionalReturn.isPresent()) {
            return new ResponseEntity<>("Book is not borrowed", HttpStatus.BAD_REQUEST);
        }

        Borrow returnedBook = optionalReturn.get();
        return new ResponseEntity<>(returnedBook, HttpStatus.OK);
    }

    // View Active Borrows
    @GetMapping("/active")
    public ResponseEntity<List<Borrow>> getActiveBorrows() {
        List<Borrow> borrows = borrowService.getActiveBorrows();
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    // View Active Borrows by User
    @GetMapping("/active/user/{userId}")
    public ResponseEntity<List<Borrow>> getActiveBorrowsByUser(@PathVariable Long userId) throws EntityNotFoundException {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Borrow> borrows = borrowService.getActiveBorrowsByUser(user);
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }


    // View Borrow History by User
    @GetMapping("/history/user/{userId}")
    public ResponseEntity<List<Borrow>> getBorrowHistoryByUser(@PathVariable Long userId) throws EntityNotFoundException {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Borrow> borrows = borrowService.getBorrowHistoryByUser(user);
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    // View Borrow History by Book
    @GetMapping("/history/book/{bookId}")
    public ResponseEntity<List<Borrow>> getBorrowHistoryByBook(@PathVariable Long bookId) throws EntityNotFoundException {
        BookDTO book = bookService.getBookById(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Borrow> borrows = borrowService.getBorrowHistoryByBook(book);
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Borrow>> getBorrowHistory() {
        List<Borrow> borrows = borrowService.getBorrowHistory();
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    @GetMapping("/count/total")
    Long getTotalCount() {
        return borrowService.countAllBorrows();
    }

    @GetMapping("/count/active")
    Long getActiveCount() {
        return borrowService.countActiveBorrows();
    }
}