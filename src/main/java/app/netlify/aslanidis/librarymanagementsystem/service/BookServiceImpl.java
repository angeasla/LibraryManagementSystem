package app.netlify.aslanidis.librarymanagementsystem.service;


import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;
import app.netlify.aslanidis.librarymanagementsystem.repository.AuthorRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.PublisherRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import app.netlify.aslanidis.librarymanagementsystem.service.utilities.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Long getTotalCount() {
        return bookRepository.count();
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(DTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookDTO> getAllBooksWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("title"), Sort.Order.asc("author.lastname")));
        Page<Book> bookPage = bookRepository.findAllByOrderByTitleAscAuthor_LastnameAsc(pageable);

        List<BookDTO> booksDto = bookPage.stream()
                .map(DTOConverter::convertBookToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(booksDto, pageable, bookPage.getTotalElements());
    }

    @Override
    public BookDTO getBookById(Long id) throws EntityNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return DTOConverter.convertBookToDTO(book);
    }

    @Override
    public List<Book> getByIds(List<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public List<BookDTO> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findBookByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(DTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDTO createBook(BookDTO bookDTO) throws EntityNotFoundException {
        Book book = DTOConverter.convertDTOToBook(bookDTO);
        validateAuthorAndPublisher(book);
        Book savedBook = bookRepository.save(book);
        return DTOConverter.convertBookToDTO(savedBook);
    }

    @Transactional
    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws EntityNotFoundException {
        Book bookToUpdate = DTOConverter.convertDTOToBook(bookDTO);
        validateAuthorAndPublisher(bookToUpdate);

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        existingBook.setTitle(bookToUpdate.getTitle());
        existingBook.setAuthor(bookToUpdate.getAuthor());
        existingBook.setIsbn(bookToUpdate.getIsbn());
        existingBook.setPublisher(bookToUpdate.getPublisher());
        existingBook.setPages(bookToUpdate.getPages());
        existingBook.setPublicationYear(bookToUpdate.getPublicationYear());
        existingBook.setQuantity(bookToUpdate.getQuantity());

        Book updatedBook = bookRepository.save(existingBook);
        return DTOConverter.convertBookToDTO(updatedBook);
    }

    @Transactional
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long bookId) throws EntityNotFoundException {
        Book book = getBookByIdToDelete(bookId);
        bookRepository.delete(book);
    }

    @Override
    public Book getBookByIdToDelete(Long bookId) throws EntityNotFoundException {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }


    @Transactional
    @Override
    public Book updateBookQuantity(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findBooksByPublisherId(Long publisherId) {
        return bookRepository.findByPublisher_PublisherId(publisherId);
    }

    @Override
    public List<Book> findBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthor_AuthorId(authorId);
    }


    private void validateAuthorAndPublisher(Book book) throws EntityNotFoundException {
        // Check if author exists
        Author author = authorRepository.findById(book.getAuthor().getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        book.setAuthor(author);

        // Check if publisher exists
        Publisher publisher = publisherRepository.findById(book.getPublisher().getPublisherId())
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
        book.setPublisher(publisher);
    }

    // Helper method to perform validation using the BookValidator
    /*private void validateBook(BookDTO bookDTO) {
        Errors errors = new BeanPropertyBindingResult(bookDTO, "book");
        bookValidator.validate(bookDTO, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid book data");
        }
    }*/
}
