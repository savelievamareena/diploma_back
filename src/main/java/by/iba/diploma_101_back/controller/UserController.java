package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.RegisterForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") int userId, @RequestBody RegisterForm userDetails, HttpServletResponse response) {
        ApiResponse apiResponse = new ApiResponse();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            apiResponse.setMessage("Email already Exists");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/email")
    public User getUserByEmail(@RequestBody Map<String, String> body) {
        User result = userRepository.findByEmail(body.get("email"));
        return result;
    }
}
