package by.iba.diploma_101_back;

import by.iba.diploma_101_back.helpers.Security;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserEntityRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserByIdTest(){
        User user = userRepository.findById(14).get();
        assertEquals("persempre1@yandex.ru",user.getEmail());
    }

    @Test
    public void getAllUsersTest(){
        List<User> userList = userRepository.findAll();
        assertEquals(18, userList.size());
    }

    @Test
    public void createUserTest() throws NoSuchAlgorithmException {
        int random_int = (int)Math.floor(Math.random()*(10000-10+1)+10);
        String email = "testemail" + random_int + "@gmail.com";
        String hashedPass = Security.securePass("12345");

        User user = new User();
        user.setDateOfBirth("2000-02-13");
        user.setEmail(email);
        user.setPhoneNumber("383838");
        user.setPassword(hashedPass);
        user.setFirstName("Helena");
        user.setLastName("Aneleh");
        assertNotNull(userRepository.save(user));
    }
}