package softuni.photostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhotoStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoStoreApplication.class, args);
    }

}
