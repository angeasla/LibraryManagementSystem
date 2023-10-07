package app.netlify.aslanidis.librarymanagementsystem.repository;

import app.netlify.aslanidis.librarymanagementsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    List<User> findAllByOrderByLastnameAscFirstnameAsc();
    Page<User> findAllByOrderByLastnameAscFirstnameAsc(Pageable pageable);
}
