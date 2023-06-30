/*
package app.netlify.aslanidis.librarymanagementsystem.validator;

import app.netlify.aslanidis.librarymanagementsystem.dto.UserDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
       return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        // Validate user fields
        if (userDTO.getFirstname() == null || userDTO.getFirstname().isEmpty()) {
            errors.rejectValue("firstname", "user.firstname.empty", "User name cannot be empty");
        }

        if (userDTO.getPhone() == null || userDTO.getPhone().isEmpty()) {
            errors.rejectValue("phone", "user.phone.empty", "User phone cannot be empty");
        }

        // Additional validation. Checks if the phone is unique.
        validatePhoneUniqueness(userDTO.getPhone(), errors);
    }

    private void validatePhoneUniqueness(String phone, Errors errors) {
        if (phone != null && userRepository.findByPhone(phone).isPresent()) {
            errors.rejectValue("phone", "user.phone.duplicate", "Phone number is already taken");
        }
    }
}
*/
