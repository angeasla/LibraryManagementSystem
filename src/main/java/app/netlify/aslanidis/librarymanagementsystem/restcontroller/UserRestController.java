package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.model.User;
import app.netlify.aslanidis.librarymanagementsystem.service.IUserService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final IUserService userService;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<User> users = userService.getAllUsersWithPagination(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) throws EntityNotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/search-by-phone/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) throws EntityNotFoundException {
        User user = userService.getUserByPhone(phone);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") Long userId, @RequestBody User user) throws EntityNotFoundException {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) throws EntityNotFoundException {
        userService.deleteUser(userId);
    }

    @GetMapping("/active-borrows")
    public ResponseEntity<List<User>> getAllUsersWithActiveBorrows() {
        System.out.println("Endpoint /active-borrows was called");
        List<User> users = userService.getAllUsersWithActiveBorrows();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count/total")
    public Long getTotalCount() {
        return userService.countUsers();
    }
}
