package softuni.photostore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.photostore.web.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PhotoStoreApplicationTests {

    @Autowired
    private HomeController homeController;
    @Autowired
    private BagsController bagsController;
    @Autowired
    private CameraController cameraController;
    @Autowired
    private CommonsController commonsController;
    @Autowired
    private CartController cartController;
    @Autowired
    private FlashesController flashesController;
    @Autowired
    private LensesController lensesController;
    @Autowired
    private TripodsController tripodsController;
    @Autowired
    private UserEditController userEditController;
    @Autowired
    private UserLoginController userLoginController;
    @Autowired
    private UserRegistrationController userRegistrationController;

    @Test
    void contextLoads() {
        assertThat(homeController).isNotNull();
        assertThat(bagsController).isNotNull();
        assertThat(cameraController).isNotNull();
        assertThat(cartController).isNotNull();
        assertThat(commonsController).isNotNull();
        assertThat(flashesController).isNotNull();
        assertThat(lensesController).isNotNull();
        assertThat(tripodsController).isNotNull();
        assertThat(userEditController).isNotNull();
        assertThat(userLoginController).isNotNull();
        assertThat(userRegistrationController).isNotNull();
    }

}
