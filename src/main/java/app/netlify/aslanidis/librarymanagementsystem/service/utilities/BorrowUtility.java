package app.netlify.aslanidis.librarymanagementsystem.service.utilities;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.BorrowRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowUtility {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public User retrieveUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Book retrieveBook(Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
}
