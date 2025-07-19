package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    
    List<BookCopy> findByBook(Book book);
    
    List<BookCopy> findByBookAndIsAvailable(Book book, Boolean isAvailable);
    
    Optional<BookCopy> findFirstByBookAndIsAvailable(Book book, Boolean isAvailable);
    
    @Query("SELECT COUNT(bc) FROM BookCopy bc WHERE bc.book = :book AND bc.isAvailable = true")
    Integer countAvailableCopiesByBook(Book book);
    
    @Query("SELECT COUNT(bc) FROM BookCopy bc WHERE bc.book = :book")
    Integer countTotalCopiesByBook(Book book);
}