package by.iba.diploma_101_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Diploma101BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(Diploma101BackApplication.class, args);
    }

}
