package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.BorrowId;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.BorrowRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.UserRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import app.netlify.aslanidis.librarymanagementsystem.service.utilities.BorrowUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceImpl implements IBorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final BookServiceImpl bookService;
    private final BorrowUtility borrowUtility;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, BookRepository bookRepository, BookServiceImpl bookService, BorrowUtility borrowUtility, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.borrowUtility = borrowUtility;
    }

    @Transactional
    @Override
    public Optional<Borrow> borrowBook(Long userId, Long bookId) {
        User user = borrowUtility.retrieveUser(userId);
        Book book = bookRepository.findById(bookId).orElse(null);

        // Check if user or book is null or if the book is out of stock
        if (user == null || book == null || book.getQuantity() <= 0) {
            return Optional.empty();
        }

        Borrow borrow = new Borrow();
        BorrowId borrowId = new BorrowId();
        borrowId.setUserId(userId);
        borrowId.setBookId(bookId);
        borrowId.setBorrowTimestamp(LocalDateTime.now());
        borrow.setId(borrowId);

        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(new Date());
        borrow.setReturned(0);  // 0 = Not returned

        book.removeBook();  // Reduce book quantity
        bookService.updateBookQuantity(book);

        return Optional.of(borrowRepository.save(borrow));
    }

    @Transactional
    @Override
    public Optional<Borrow> returnBook(Long userId, Long bookId) throws EntityNotFoundException {
        User user = borrowUtility.retrieveUser(userId);
        Book book = bookService.getBookByIdToDelete(bookId);

        if (user == null || book == null) {
            return Optional.empty();
        }

        Optional<Borrow> borrow = borrowRepository.findLatestBorrowByUserAndBookAndReturned(user, book, 0); // 0 = Not returned
        if (borrow.isPresent()) {
            borrow.get().setReturnDate(new Date());
            borrow.get().setReturned(1);  // 1 = Returned

            book.addBook();  // Increase book quantity
            bookService.updateBookQuantity(book);

            return Optional.of(borrowRepository.save(borrow.get()));
        }
        return Optional.empty();  // Book is not borrowed from this user or has be returned
    }

    @Override
    public List<Borrow> getActiveBorrows() {
        return borrowRepository.findByReturnedFalse();
    }

    @Override
    public List<Borrow> getActiveBorrowsByUser(User user) {
        return borrowRepository.findByUserAndReturnedFalse(user);
    }

    @Override
    public List<Borrow> getBorrowHistory() {
        return borrowRepository.findByReturnedTrue();
    }

    @Override
    public Long countAllBorrows() {
        return borrowRepository.countAllBorrows();
    }

    @Override
    public Long countActiveBorrows() {
        return borrowRepository.countActiveBorrows();
    }

    @Override
    public List<Borrow> getBorrowHistoryByUser(User user) {
        return borrowRepository.findByUser(user);
    }

    @Override
    public List<Borrow> getBorrowHistoryByBook(BookDTO book) {
        return borrowRepository.findByBook(book);
    }

    // Helper method to perform validation using the BorrowValidator
    /*private void validateBorrow(BorrowDTO borrowDto) {
        Errors errors = new BeanPropertyBindingResult(borrowDto, "borrow");
        borrowValidator.validate(borrowDto, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid borrow data");
        }
    }*/
}
