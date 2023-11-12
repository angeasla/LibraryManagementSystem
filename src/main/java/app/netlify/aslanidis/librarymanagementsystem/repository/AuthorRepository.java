package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAll();
    Page<Author> findAllByOrderByLastnameAsc(Pageable pageable);
    Author findAuthorByLastnameContainingIgnoreCase(String lastname);
}
