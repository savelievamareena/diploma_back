package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.forms.LoginForm;
import by.iba.diploma_101_back.forms.RegisterForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
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

    @PostMapping("login")
    public ResponseEntity<?> logInFunc(@RequestBody LoginForm loginForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        ApiResponse apiResponse = new ApiResponse();

        User user = this.getUserByEmail(loginForm.getEmail());
        if(user != null) {
            String pass = loginForm.getPassword();
            byte[] bytesOfMessage = pass.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
            byte[] theMD5digest = md.digest(bytesOfMessage);
            String hashedPass = DatatypeConverter.printHexBinary(theMD5digest);

            if(Objects.equals(user.getPassword(), hashedPass)) {
                Cookie loginCookie = new Cookie("authKey", hashedPass);
                loginCookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(loginCookie);

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
    public HttpServletResponse registerFunc(@RequestBody RegisterForm registerForm) {

        return null;
    }







}
