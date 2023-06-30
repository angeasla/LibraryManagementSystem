package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.common.Constants;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.BorrowRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowServiceImpl implements IBorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Borrow> getAll() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow getById(Long id) {
        return borrowRepository.findById(id).get();
    }

    @Override
    public List<Borrow> getAllUnreturned() {
        return borrowRepository.findByReturned(Constants.BOOK_NOT_RETURNED);
    }

    @Override
    public Borrow addNew(Borrow borrow) {
        borrow.setBorrowDate(new Date());
        borrow.setReturned(Constants.BOOK_NOT_RETURNED);
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow save(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    @Override
    public Long getCountByUser(User user) {
        return borrowRepository.countByUserAndReturned(user, Constants.BOOK_NOT_RETURNED);
    }

    @Override
    public List<Book> getBooksByIds(List<Long> ids) {
        return bookRepository.findAllByBookIdIn(ids);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    // Helper method to perform validation using the BorrowValidator
    /*private void validateBorrow(BorrowDTO borrowDto) {
        Errors errors = new BeanPropertyBindingResult(borrowDto, "borrow");
        borrowValidator.validate(borrowDto, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid borrow data");
        }
    }*/
}
