package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.User;

import java.util.List;

public interface IBorrowService {
    List<Borrow> getAll();
    Borrow getById(Long id);
    List<Borrow> getAllUnreturned();
    Borrow addNew(Borrow borrow);
    Borrow save(Borrow borrow);
    Long getCountByUser(User user);
    List<Book> getBooksByIds(List<Long> ids);
    User getUserById(Long userId);
}
