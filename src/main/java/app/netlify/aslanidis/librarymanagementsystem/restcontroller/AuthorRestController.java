package app.netlify.aslanidis.librarymanagementsystem.restcontroller;
import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import app.netlify.aslanidis.librarymanagementsystem.service.IAuthorService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final IAuthorService authorService;

    @Autowired
    public AuthorRestController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{authorId}")
    public Author getAuthorById(@PathVariable("authorId") Long authorId) throws EntityNotFoundException {
        return authorService.getAuthorById(authorId);
    }

    @GetMapping("/search-by-name/{authorLastname}")
    public ResponseEntity<Author> getAuthorByLastName(@PathVariable String authorLastname) throws EntityNotFoundException {
        Author author = authorService.getAuthorByLastName(authorLastname);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
}

    @PutMapping("/{authorId}")
    public Author updateAuthor(@PathVariable("authorId") Long authorId, @RequestBody Author author) throws EntityNotFoundException {
        return authorService.updateAuthor(authorId, author);
    }

    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable("authorId") Long authorId) throws EntityNotFoundException {
        authorService.deleteAuthor(authorId);
    }
}
