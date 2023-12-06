package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.AuthorDTO;
import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.repository.AuthorRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import app.netlify.aslanidis.librarymanagementsystem.service.utilities.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements IAuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Page<AuthorDTO> getAllAuthorsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("lastname")));
        Page<Author> authorPage = authorRepository.findAllByOrderByLastnameAsc(pageable);

        List<AuthorDTO> authorsDto = authorPage.stream()
                .map(DTOConverter::convertAuthorToShortDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(authorsDto, pageable, authorPage.getTotalElements());
    }

    @Override
    public Long countAuthors() {
        return authorRepository.count();
    }

    @Override
    public Author getAuthorById(Long authorId) throws EntityNotFoundException {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @Override
    public Author getAuthorByLastName(String lastname) {
        return authorRepository.findAuthorByLastnameContainingIgnoreCase(lastname);

    }

    @Transactional
    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Long authorId, Author author) throws EntityNotFoundException {
        if (!authorRepository.existsById(authorId)) {
            throw new EntityNotFoundException("Author not found");
        }
        //validateAuthor(author);
        author.setAuthorId(authorId);
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
