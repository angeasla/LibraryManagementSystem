package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BorrowedBook;

import java.util.List;

public interface IBorrowedBookService {
    List<BorrowedBook> getAll();
    BorrowedBook getById(Long id);
    Long getCountByBook(Book book);
    BorrowedBook save(BorrowedBook borrowedBook);
    BorrowedBook addNew(BorrowedBook borrowedBook);
    public void deleteBorrowBook(BorrowedBook borrowedBook);
}
