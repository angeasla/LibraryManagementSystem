/*
package app.netlify.aslanidis.librarymanagementsystem.validator;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookDTO book = (BookDTO) target;

        // Validate book fields
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            errors.rejectValue("title", "book.title.empty", "Book title cannot be empty");
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            errors.rejectValue("author", "book.author.empty", "Book author cannot be empty");
        }

        if (book.getPublisher() == null || book.getPublisher().isEmpty()) {
            errors.rejectValue("publisher", "book.publisher.empty", "Book publisher cannot be empty");
        }
    }
}
*/
