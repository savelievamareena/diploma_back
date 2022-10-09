package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.LoginForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UserController {

    String hashingAlgorithm = "MD5";
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

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") int userId, @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());

        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    public User getUserByEmail(String userIEmail) {
        return userRepository.findByEmail(userIEmail);
    }

    @PostMapping("login")
    public ApiResponse logInFunc(@RequestBody LoginForm loginForm) throws NoSuchAlgorithmException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse(false);

        User user = this.getUserByEmail(loginForm.getEmail());
        if(user != null) {
            String pass = loginForm.getPassword();
            byte[] bytesOfMessage = pass.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
            byte[] theMD5digest = md.digest(bytesOfMessage);
            String hashedPass = DatatypeConverter.printHexBinary(theMD5digest);

            if(Objects.equals(user.getPassword(), hashedPass)) {
                apiResponse.setResponse(true);
                apiResponse.setMessage("success");
            }else {
                apiResponse.setMessage("wrong password: " + hashedPass);
            }
        }else {
            apiResponse.setMessage("no user");
        }
        return apiResponse;
    }
}
