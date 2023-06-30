/*
package app.netlify.aslanidis.librarymanagementsystem.validator;

import app.netlify.aslanidis.librarymanagementsystem.dto.BorrowDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Borrow;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BorrowValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BorrowDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BorrowDTO borrowDTO = (BorrowDTO) target;

        // Validate borrow fields
        if (borrowDTO.getUser() == null) {
            errors.rejectValue("user", "borrow.user.empty", "User cannot be empty");
        }

        if (borrowDTO.getBook() == null) {
            errors.rejectValue("book", "borrow.book.empty", "Book cannot be empty");
        }

        if (borrowDTO.getBorrowDate() == null) {
            errors.rejectValue("borrowDate", "borrow.borrowDate.empty", "Borrow date cannot be empty");
        }

        if (borrowDTO.getReturnDate() != null && borrowDTO.getReturnDate().isBefore(borrowDTO.getBorrowDate())) {
            errors.rejectValue("returnDate", "borrow.returnDate.invalid", "Return date cannot be before borrow date");
        }
    }
}
*/
