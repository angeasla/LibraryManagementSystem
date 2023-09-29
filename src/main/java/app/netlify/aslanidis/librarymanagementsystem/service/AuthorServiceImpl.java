package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import app.netlify.aslanidis.librarymanagementsystem.repository.AuthorRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long authorId) throws EntityNotFoundException {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @Override
    public Author getAuthorByLastName(String lastname) {
        return authorRepository.findAuthorByLastname(lastname);

    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long authorId, Author author) throws EntityNotFoundException {
        if (!authorRepository.existsById(authorId)) {
            throw new EntityNotFoundException("Author not found");
        }
        //validateAuthor(author);
        author.setAuthorId(authorId);
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
