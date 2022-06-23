package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.entity.accounts.UserRole;
import softuni.photostore.model.entity.enums.UserRoleEnum;
import softuni.photostore.service.impl.AppUser;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AppUserTest {

    private UserRole userRole;
    private AppUser appUser;

    @Test
    public void testBaseConstructor() {
        appUser = new AppUser("test", "test", Set.of(new SimpleGrantedAuthority("ROLE_USER")));
        assertThat(appUser).isNotNull();
        assertThat(appUser.getUserIdentifier()).isNotNull();
        assertThat(appUser.getUserIdentifier()).isEqualToIgnoringCase("test");
    }

    @Test
    public void testAdvConstructor() {
        appUser = new AppUser("test", "test", true, true, true, true, Set.of(new SimpleGrantedAuthority("ROLE_USER")));
        assertThat(appUser).isNotNull();
        assertThat(appUser.getUserIdentifier()).isNotNull();
        assertThat(appUser.getUserIdentifier()).isEqualToIgnoringCase("test");
    }

}
