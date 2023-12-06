package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.repository.BorrowRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.UserRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BorrowRepository borrowRepository) {
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByLastnameAscFirstnameAsc();
    }

    @Override
    public Page<User> getAllUsersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("lastname"), Sort.Order.asc("firstname")));
        return userRepository.findAllByOrderByLastnameAscFirstnameAsc(pageable);
    }

    @Override
    public User getUserByPhone(String phone) throws EntityNotFoundException {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User getUserById(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long userId, User user) throws EntityNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        user.setUserId(userId);
        //validateUser(user);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) throws EntityNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsersWithActiveBorrows() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            int activeBorrowCount = borrowRepository.countByUserAndReturned(user, 0);
            user.setActiveBorrowCount(activeBorrowCount);
            System.out.println("User: " + user.getUserId() + ", Active Borrow Count: " + activeBorrowCount);
        }
        return users;
    }

    public Long countUsers() {
        return userRepository.count();
    }

    // Helper method to perform validation using the UserValidator
    /*private void validateUser(UserDTO userDto) {
        Errors errors = new BeanPropertyBindingResult(userDto, "user");
        userValidator.validate(userDto, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid user data");
        }
    }*/

}
