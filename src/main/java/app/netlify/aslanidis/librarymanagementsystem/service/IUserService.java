package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserByPhone(String phone) throws EntityNotFoundException;
    User getUserById(Long userId) throws EntityNotFoundException;
    User createUser(User user);
    User updateUser(Long userId, User user) throws EntityNotFoundException;
    void deleteUser(Long userId) throws EntityNotFoundException;
    boolean hasUsage(User user);
}
