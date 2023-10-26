package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Optional<Borrow> findByUserAndBookAndReturned(User user, Book book, Integer returned);
    List<Borrow> findByReturnedFalse();  // Find active borrows
    List<Borrow> findByUserAndReturnedFalse(User user);  // Active borrows from a user
    List<Borrow> findByUser(User user);  // User's borrow history
    List<Borrow> findByBook(Book book);  // Book's borrow history
    int countByUserAndReturned(User user, Integer returned);
}
