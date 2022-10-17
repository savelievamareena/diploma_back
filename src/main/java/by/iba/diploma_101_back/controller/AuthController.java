package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.forms.LoginForm;
import by.iba.diploma_101_back.forms.RegisterForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Review;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    String hashingAlgorithm = "MD5";

    private final UserRepository userRepository;
    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String userIEmail) {
        return userRepository.findByEmail(userIEmail);
    }

    private static String securePass(String password, String hashingAlgorithm) throws NoSuchAlgorithmException {
        byte[] bytesOfMessage = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
        byte[] theMD5digest = md.digest(bytesOfMessage);
        return DatatypeConverter.printHexBinary(theMD5digest);
    }

    @PostMapping("login")
    public ResponseEntity<?> logInFunc(@RequestBody LoginForm loginForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        ApiResponse apiResponse = new ApiResponse();

        User user = this.getUserByEmail(loginForm.getEmail());
        if(user != null) {
            String pass = loginForm.getPassword();
            String hashedPass = securePass(pass, hashingAlgorithm);

            if(Objects.equals(user.getPassword(), hashedPass)) {
                Cookie loginCookie = new Cookie("authKey", hashedPass);
                loginCookie.setMaxAge(7 * 24 * 60 * 60);
                loginCookie.setPath("/");
                response.addCookie(loginCookie);
                apiResponse.setCookie(hashedPass);
            }else {
                apiResponse.setMessage("Wrong Password");
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(apiResponse);
            }
        }else {
            apiResponse.setMessage("No User");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> registerFunc(@RequestBody RegisterForm registerForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        ApiResponse apiResponse = new ApiResponse();

        if(!Objects.equals(registerForm.getPassword(), registerForm.getConfirmPassword())) {
            apiResponse.setMessage("Passwords do not match");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        if(this.getUserByEmail(registerForm.getEmail()) != null) {
            apiResponse.setMessage("User already exists");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setPhoneNumber(registerForm.getPhoneNumber());
        user.setRole("user");

        String pass = registerForm.getPassword();
        String hashedPass = securePass(pass, hashingAlgorithm);
        user.setPassword(hashedPass);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            apiResponse.setMessage("Error");
        }

        Cookie loginCookie = new Cookie("authKey", hashedPass);
        loginCookie.setMaxAge(3 * 24 * 60 * 60);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);
        apiResponse.setCookie(hashedPass);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<?> logoutFunc(@CookieValue(name = "authKey") String authKey, HttpServletResponse response) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Cookie loginCookie = new Cookie("authKey", authKey) ;
            loginCookie.setMaxAge(0);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
        } catch (Exception e) {
            apiResponse.setMessage("Error");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
