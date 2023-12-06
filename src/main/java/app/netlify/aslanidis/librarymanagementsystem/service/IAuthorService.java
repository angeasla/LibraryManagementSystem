package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.AuthorDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long authorId) throws EntityNotFoundException;
    Author getAuthorByLastName(String Lastname);
    Author createAuthor(Author author);
    Author updateAuthor(Long authorId, Author author) throws EntityNotFoundException;
    void deleteAuthor(Long authorId);
    Page<AuthorDTO> getAllAuthorsWithPagination(int page, int size);
    Long countAuthors();
}
