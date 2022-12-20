package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.forms.LoginForm;
import by.iba.diploma_101_back.forms.RegisterForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.helpers.Security;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String userIEmail) {
        return userRepository.findByEmail(userIEmail);
    }

    private static void setCookies(User user, String hashedPass, HttpServletResponse response) {
        Cookie loginCookie = new Cookie("authKey", hashedPass);
        loginCookie.setMaxAge(3 * 24 * 60 * 60);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);

        Cookie userCookie = new Cookie("role", user.getRole());
        userCookie.setMaxAge(3 * 24 * 60 * 60);
        userCookie.setPath("/");
        response.addCookie(userCookie);

        Cookie idCookie = new Cookie("userId", Integer.toString(user.getId()));
        idCookie.setMaxAge(3 * 24 * 60 * 60);
        idCookie.setPath("/");
        response.addCookie(idCookie);
    }

    @PostMapping("login")
    public ResponseEntity<?> logInFunc(@RequestBody LoginForm loginForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        ApiResponse apiResponse = new ApiResponse();

        User user = this.getUserByEmail(loginForm.getEmail());
        if(user != null) {
            String pass = loginForm.getPassword();
            String hashedPass = Security.securePass(pass);

            if(Objects.equals(user.getPassword(), hashedPass)) {
                setCookies(user, hashedPass, response);

                apiResponse.setCookie(hashedPass);
                apiResponse.setRole(user.getRole());
            }else {
                apiResponse.setMessage("Неверный пароль");
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(apiResponse);
            }
        }else {
            apiResponse.setMessage("Пользователь не существует");
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
            apiResponse.setMessage("Пароли не совпадают");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        if(this.getUserByEmail(registerForm.getEmail()) != null) {
            apiResponse.setMessage("Пользователь уже существует");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setDateOfBirth(registerForm.getDateOfBirth());
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setPhoneNumber(registerForm.getPhoneNumber());
        if(registerForm.isAdmin()) {
            user.setRole("admin");
        }else {
            user.setRole("user");
        }

        String pass = registerForm.getPassword();
        String hashedPass = Security.securePass(pass);
        user.setPassword(hashedPass);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            apiResponse.setMessage("Ошибка");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        setCookies(user, hashedPass, response);

        apiResponse.setCookie(hashedPass);
        apiResponse.setRole(user.getRole());

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

            Cookie userCookie = new Cookie("role", "0");
            userCookie.setMaxAge(0);
            userCookie.setPath("/");
            response.addCookie(userCookie);

            Cookie idCookie = new Cookie("userId", "0");
            idCookie.setMaxAge(0);
            idCookie.setPath("/");
            response.addCookie(idCookie);

        } catch (Exception e) {
            apiResponse.setMessage("Ошибка");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
