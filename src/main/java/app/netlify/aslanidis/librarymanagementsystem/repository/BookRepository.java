package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByTitleContainingIgnoreCase(String title);
    List<Book> findByPublisher_PublisherId(Long publisherId);
    List<Book> findByAuthor_AuthorId(Long authorId);
    Page<Book> findAllByOrderByTitleAscAuthor_LastnameAsc(Pageable pageable);
}
