package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.BorrowId;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, BorrowId> {
    Optional<Borrow> findByUserAndBookCopyAndReturned(User user, BookCopy bookCopy, Integer returned);
    List<Borrow> findByReturnedFalse();  // Find active borrows
    List<Borrow> findByUserAndReturnedFalse(User user);  // Active borrows from a user
    List<Borrow> findByUser(User user);  // User's borrow history
    List<Borrow> findByBookCopy_Book(Book book);  // Book's borrow history (through BookCopy)
    int countByUserAndReturned(User user, Integer returned);
    List<Borrow> findByReturnedTrue(); // Find borrows history

    @Query("SELECT COUNT(b) from Borrow b")
    Long countAllBorrows();

    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.returned = 0")
    Long countActiveBorrows();
}
