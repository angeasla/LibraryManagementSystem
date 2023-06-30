package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.common.Constants;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BorrowedBook;
import app.netlify.aslanidis.librarymanagementsystem.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowedBookServiceImpl implements IBorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Override
    public List<BorrowedBook> getAll() {
        return borrowedBookRepository.findAll();
    }

    @Override
    public BorrowedBook getById(Long id) {
        return borrowedBookRepository.getById(id);
    }

    @Override
    public Long getCountByBook(Book book) {
        return borrowedBookRepository.countByBookAndReturned(book, Constants.BOOK_NOT_RETURNED);
    }

    @Override
    public BorrowedBook save(BorrowedBook borrowedBook) {
        return borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public BorrowedBook addNew(BorrowedBook borrowedBook) {
        borrowedBook.setReturned(Constants.BOOK_NOT_RETURNED);
        return borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public void deleteBorrowBook(BorrowedBook borrowedBook) {
        borrowedBookRepository.deleteById(borrowedBook.getId());
    }
}
