package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.common.Constants;
import app.netlify.aslanidis.librarymanagementsystem.dto.BorrowRequest;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.BorrowedBook;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.service.IBookService;
import app.netlify.aslanidis.librarymanagementsystem.service.IBorrowService;
import app.netlify.aslanidis.librarymanagementsystem.service.IBorrowedBookService;
import app.netlify.aslanidis.librarymanagementsystem.service.IUserService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/borrows/")
public class BorrowRestController {

    private final IUserService userService;
    private final IBookService bookService;
    private final IBorrowService borrowService;
    private final IBorrowedBookService borrowedBookService;

    @Autowired
    public BorrowRestController(IUserService userService,
                                IBookService bookService,
                                IBorrowService borrowService,
                                IBorrowedBookService borrowedBookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.borrowService = borrowService;
        this.borrowedBookService = borrowedBookService;
    }

    /*@PostMapping
    public String borrow(@RequestParam Map<String, String> payload) throws EntityNotFoundException {
        String userIdStr = (String) payload.get("user");
        String[] bookIdsStr = payload.get("books").toString().split(",");

        Long userId = null;
        List<Long> bookIds = new ArrayList<Long>();
        try {
            userId = Long.parseLong( userIdStr );
            for(int k=0 ; k < bookIdsStr.length ; k++) {
                bookIds.add(Long.parseLong(bookIdsStr[k]));
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return "invalid number format";
        }

        User user = userService.getUserById(userId);
        List<Book> books = bookService.getByIds(bookIds);

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow = borrowService.addNew(borrow);

        for( int k=0 ; k<books.size() ; k++ ) {
            Book book = books.get(k);
            book.setStatus(Constants.BOOK_BORROWED);
            book = bookService.save(book);

            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBook(book);
            borrowedBook.setBorrow(borrow);
            borrowedBookService.addNew(borrowedBook);

        }

        return "success";
    }

    @GetMapping("/{id}/return/all/")
    public String returnAll(@PathVariable(name = "id") Long id) {
        Borrow borrow = borrowService.getById(id);
        if (borrow != null) {
            List<BorrowedBook> borrowedBooks = borrow.getBorrowedBooks();
            for( int k=0 ; k<borrowedBooks.size() ; k++ ) {
                BorrowedBook borrowedBook = borrowedBooks.get(k);
                borrowedBook.setReturned( Constants.BOOK_RETURNED );
                borrowedBookService.save(borrowedBook);

                Book book = borrowedBook.getBook();
                book.setStatus( Constants.BOOK_AVAILABLE );
                bookService.save(book);
            }

            borrow.setReturned( Constants.BOOK_RETURNED );
            borrowService.save(borrow);

            return "successful";
        } else {
            return "unsuccessful";
        }
    }

    @PostMapping("/{id}/return/")
    public String returnSelected(@RequestParam Map<String, String> payload, @PathVariable(name = "id") Long id) {
        Borrow borrow = borrowService.getById(id);
        String[] borrowedBooksIds = payload.get("ids").split(",");
        if( borrow != null ) {

            List<BorrowedBook> issuedBooks = borrow.getBorrowedBooks();
            for( int k=0 ; k<issuedBooks.size() ; k++ ) {
                BorrowedBook borrowedBook = issuedBooks.get(k);
                if( Arrays.asList(borrowedBooksIds).contains(borrowedBook.getId().toString() ) ) {
                    borrowedBook.setReturned( Constants.BOOK_RETURNED );
                    borrowedBookService.save(borrowedBook);

                    Book book = borrowedBook.getBook();
                    book.setStatus( Constants.BOOK_AVAILABLE );
                    bookService.save(book);
                }
            }

            return "successful";
        } else {
            return "unsuccessful";
        }
    }*/

    /*@PostMapping
    public ResponseEntity<?> borrow(@RequestParam Map<String, String> payload) throws EntityNotFoundException {
        String userIdStr = payload.get("user");
        String[] bookIdsStr = payload.get("books").split(",");
        // Validation for userId and bookIds
        if (userIdStr == null || bookIdsStr.length == 0) {
            return new ResponseEntity<>("User ID or Book ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        Long userId = null;
        List<Long> bookIds = new ArrayList<Long>();
        try {
            userId = Long.parseLong(userIdStr);
            for (String bookIdStr : bookIdsStr) {
                bookIds.add(Long.parseLong(bookIdStr));
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Invalid number format", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        List<Book> books = bookService.getByIds(bookIds);
        if (books.isEmpty()) {
            return new ResponseEntity<>("Books not found", HttpStatus.NOT_FOUND);
        }

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow = borrowService.addNew(borrow);

        for (Book book : books) {
            if (book.getStatus().equals(Constants.BOOK_BORROWED)) {
                return new ResponseEntity<>("Book is already borrowed", HttpStatus.BAD_REQUEST);
            }
            book.setStatus(Constants.BOOK_BORROWED);
            book = bookService.save(book);

            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBook(book);
            borrowedBook.setBorrow(borrow);
            borrowedBookService.addNew(borrowedBook);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }*/

    @PostMapping
    public ResponseEntity<?> borrow(@RequestBody BorrowRequest borrowRequest) throws EntityNotFoundException {
        Long userId = borrowRequest.getUser();
        List<Long> bookIds = borrowRequest.getBooks();
        // Validation for userId and bookIds
        if (userId == null || bookIds.isEmpty()) {
            return new ResponseEntity<>("User ID or Book ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        List<Book> books = bookService.getByIds(bookIds);
        if (books.isEmpty()) {
            return new ResponseEntity<>("Books not found", HttpStatus.NOT_FOUND);
        }

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow = borrowService.addNew(borrow);

        for (Book book : books) {
            if (book.getStatus().equals(Constants.BOOK_BORROWED)) {
                return new ResponseEntity<>("Book is already borrowed", HttpStatus.BAD_REQUEST);
            }
            book.setStatus(Constants.BOOK_BORROWED);
            book = bookService.save(book);

            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBook(book);
            borrowedBook.setBorrow(borrow);
            borrowedBookService.addNew(borrowedBook);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    /*@PutMapping("/{id}/return/all/")
    public ResponseEntity<?> returnAll(@PathVariable(name = "id") Long id) {
        Borrow borrow = borrowService.getById(id);
        if (borrow != null) {
            List<BorrowedBook> borrowedBooks = borrow.getBorrowedBooks();
            for (BorrowedBook borrowedBook : borrowedBooks) {
                borrowedBook.setReturned( Constants.BOOK_RETURNED );
                borrowedBookService.save(borrowedBook);

                Book book = borrowedBook.getBook();
                book.setStatus( Constants.BOOK_AVAILABLE );
                bookService.save(book);
            }

            borrow.setReturned( Constants.BOOK_RETURNED );
            borrowService.save(borrow);

            return new ResponseEntity<>("Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unsuccessful", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/return/")
    public ResponseEntity<?> returnSelected(@RequestParam Map<String, String> payload, @PathVariable(name = "id") Long id) {
        Borrow borrow = borrowService.getById(id);
        String ids = payload.get("ids");
        // Validation for ids
        if(ids == null) {
            return new ResponseEntity<>("IDs cannot be null", HttpStatus.BAD_REQUEST);
        }

        String[] borrowedBooksIds = ids.split(",");
        if (borrow != null) {
            List<BorrowedBook> issuedBooks = borrow.getBorrowedBooks();
            for (BorrowedBook borrowedBook : issuedBooks) {
                if( Arrays.asList(borrowedBooksIds).contains(borrowedBook.getId().toString() ) ) {
                    borrowedBook.setReturned( Constants.BOOK_RETURNED );
                    borrowedBookService.save(borrowedBook);

                    Book book = borrowedBook.getBook();
                    book.setStatus( Constants.BOOK_AVAILABLE );
                    bookService.save(book);
                }
            }

            return new ResponseEntity<>("Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unsuccessful", HttpStatus.NOT_FOUND);
        }
    }*/

   /* @PutMapping("/return/{borrowedBookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long borrowedBookId) throws EntityNotFoundException {
        BorrowedBook borrowedBook = borrowedBookService.getById(borrowedBookId);
        if (borrowedBook == null) {
            return new ResponseEntity<>("Borrowed book not found", HttpStatus.NOT_FOUND);
        }
        Book book = borrowedBook.getBook();
        book.setStatus(Constants.BOOK_AVAILABLE);
        bookService.save(book);
        borrowedBookService.deleteBorrowBook(borrowedBook);
        return new ResponseEntity<>("Book returned successfully", HttpStatus.OK);
    }*/

    @PutMapping("/return/{borrowedBookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long borrowedBookId) throws EntityNotFoundException {
        BorrowedBook borrowedBook = borrowedBookService.getById(borrowedBookId);
        if (borrowedBook == null) {
            return new ResponseEntity<>("Borrowed book not found", HttpStatus.NOT_FOUND);
        }

        // Set the status of the book to "available".
        Book book = borrowedBook.getBook();
        book.setStatus(Constants.BOOK_AVAILABLE);
        bookService.save(book);

        // Set the status of the borrow to "returned".
        Borrow borrow = borrowedBook.getBorrow();
        borrow.setReturned(Constants.BOOK_RETURNED);
        borrowService.save(borrow);

        borrowedBookService.deleteBorrowBook(borrowedBook);

        return new ResponseEntity<>("Book returned successfully", HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        List<Borrow> borrows = borrowService.getAll();
        return ResponseEntity.ok(borrows);
    }
}