package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findPublisherByNameContainingIgnoreCase(String name);
    List<Publisher> findAll();
    Page<Publisher> findAllByOrderByNameAsc(Pageable pageable);
}
