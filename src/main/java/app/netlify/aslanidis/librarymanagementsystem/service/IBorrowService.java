package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IBorrowService {

    Optional<Borrow> borrowBook(Long userId, Long bookId);
    Optional<Borrow> returnBook(Long userId, Long bookId) throws EntityNotFoundException;
    List<Borrow> getActiveBorrows();
    List<Borrow> getActiveBorrowsByUser(User user);
    List<Borrow> getBorrowHistoryByUser(User user);
    List<Borrow> getBorrowHistoryByBook(BookDTO book);
    List<Borrow> getBorrowHistory();
    Long countAllBorrows();
    Long countActiveBorrows();
}
