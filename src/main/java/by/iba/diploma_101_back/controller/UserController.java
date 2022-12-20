package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.RegisterForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.helpers.Security;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") int userId, @RequestBody RegisterForm userDetails, HttpServletResponse response) throws NoSuchAlgorithmException {
        ApiResponse apiResponse = new ApiResponse();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if(!Objects.equals(user.getEmail(), userDetails.getEmail())) {
            user.setEmail(userDetails.getEmail());
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setDateOfBirth(userDetails.getDateOfBirth());

        if(!Objects.equals(userDetails.getPassword(), "")) {
            String pass = userDetails.getPassword();
            String hashedPass = Security.securePass(pass);
            user.setPassword(hashedPass);
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            apiResponse.setMessage("Ошибка, попробуйте позже");
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
        return userRepository.findByEmail(body.get("email"));
    }
}
