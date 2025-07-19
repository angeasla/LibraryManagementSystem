package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.BorrowId;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookCopyRepository;
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
    private final BookCopyRepository bookCopyRepository;
    private final BookServiceImpl bookService;
    private final BorrowUtility borrowUtility;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, BookServiceImpl bookService, BorrowUtility borrowUtility, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.bookService = bookService;
        this.borrowUtility = borrowUtility;
    }

    @Transactional
    @Override
    public Optional<Borrow> borrowBook(Long userId, Long bookId) {
        User user = borrowUtility.retrieveUser(userId);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null || book == null) {
            return Optional.empty();
        }

        // Find an available copy of the book
        Optional<BookCopy> availableCopy = bookCopyRepository.findFirstByBookAndIsAvailable(book, true);
        if (!availableCopy.isPresent()) {
            return Optional.empty(); // No available copies
        }

        BookCopy bookCopy = availableCopy.get();
        
        Borrow borrow = new Borrow();
        BorrowId borrowId = new BorrowId();
        borrowId.setUserId(userId);
        borrowId.setBookCopyId(bookCopy.getCopyId());
        borrowId.setBorrowTimestamp(LocalDateTime.now());
        borrow.setId(borrowId);

        borrow.setUser(user);
        borrow.setBookCopy(bookCopy);
        borrow.setBorrowDate(new Date());
        borrow.setReturned(0);  // 0 = Not returned

        // Mark the copy as unavailable
        bookCopy.setIsAvailable(false);
        bookCopyRepository.save(bookCopy);

        return Optional.of(borrowRepository.save(borrow));
    }

    @Transactional
    @Override
    public Optional<Borrow> returnBook(Long userId, Long bookId) throws EntityNotFoundException {
        User user = borrowUtility.retrieveUser(userId);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null || book == null) {
            return Optional.empty();
        }

        // Find all active borrows for this user and book (through book copies)
        List<Borrow> activeBorrows = borrowRepository.findByUserAndReturnedFalse(user);
        Optional<Borrow> targetBorrow = activeBorrows.stream()
            .filter(borrow -> borrow.getBookCopy().getBook().getBookId().equals(bookId))
            .findFirst();

        if (targetBorrow.isPresent()) {
            Borrow borrow = targetBorrow.get();
            borrow.setReturnDate(new Date());
            borrow.setReturned(1);  // 1 = Returned

            // Mark the copy as available again
            BookCopy bookCopy = borrow.getBookCopy();
            bookCopy.setIsAvailable(true);
            bookCopyRepository.save(bookCopy);

            return Optional.of(borrowRepository.save(borrow));
        }
        return Optional.empty();  // No active borrow found for this user and book
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
    public List<Borrow> getBorrowHistoryByBook(BookDTO bookDTO) {
        // Convert BookDTO to Book entity
        Book book = bookRepository.findById(bookDTO.getBookId()).orElse(null);
        if (book == null) {
            return List.of(); // Return empty list if book not found
        }
        return borrowRepository.findByBookCopy_Book(book);
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
