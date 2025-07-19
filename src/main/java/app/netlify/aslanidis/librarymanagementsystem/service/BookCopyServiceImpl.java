package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookCopyServiceImpl implements IBookCopyService {

    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public BookCopyServiceImpl(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    @Transactional
    public BookCopy createBookCopy(Book book, Integer copyNumber) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book);
        bookCopy.setCopyNumber(copyNumber);
        bookCopy.setIsAvailable(true);
        bookCopy.setCondition("NEW");
        return bookCopyRepository.save(bookCopy);
    }

    @Override
    @Transactional
    public List<BookCopy> createMultipleBookCopies(Book book, Integer quantity) {
        List<BookCopy> copies = new ArrayList<>();
        Integer existingCopies = getTotalCopiesCount(book);
        
        for (int i = 1; i <= quantity; i++) {
            BookCopy copy = createBookCopy(book, existingCopies + i);
            copies.add(copy);
        }
        return copies;
    }

    @Override
    public Optional<BookCopy> getBookCopyById(Long copyId) {
        return bookCopyRepository.findById(copyId);
    }

    @Override
    public List<BookCopy> getBookCopiesByBook(Book book) {
        return bookCopyRepository.findByBook(book);
    }

    @Override
    public List<BookCopy> getAvailableBookCopiesByBook(Book book) {
        return bookCopyRepository.findByBookAndIsAvailable(book, true);
    }

    @Override
    public Integer getAvailableCopiesCount(Book book) {
        return bookCopyRepository.countAvailableCopiesByBook(book);
    }

    @Override
    public Integer getTotalCopiesCount(Book book) {
        return bookCopyRepository.countTotalCopiesByBook(book);
    }

    @Override
    @Transactional
    public BookCopy updateBookCopy(BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    @Override
    @Transactional
    public void deleteBookCopy(Long copyId) {
        bookCopyRepository.deleteById(copyId);
    }
}