package softuni.photostore.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.photostore.service.UsersService;

@Component
public class dbInit implements CommandLineRunner {

    private final UsersService usersService;

    public dbInit(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void run(String... args) throws Exception {
        usersService.initializeRoles();
        usersService.initializeUsers();
    }
}
