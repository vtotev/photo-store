package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.entity.accounts.UserRole;
import softuni.photostore.model.entity.enums.UserRoleEnum;
import softuni.photostore.repository.UsersRepository;
import softuni.photostore.service.impl.AppUser;
import softuni.photostore.service.impl.AppUserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    private UserRole adminRole;
    private UserRole userRole;
    private AppUserServiceImpl sut;
    @Mock
    private UsersRepository mockedUsers;

    @BeforeEach
    public void setup() {
        adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);

        this.sut = new AppUserServiceImpl(mockedUsers);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user
                .setUsername("test")
                .setFirstName("test")
                .setLastName("test")
                .setActive(true)
                .setPassword("test")
                .setEmail("test@test.com")
                .setRoles(Set.of(userRole, adminRole));

        Mockito.when(mockedUsers.findByUsernameIgnoreCase("test"))
                .thenReturn(Optional.of(user));
        assertThat(sut.loadUserByUsername("test")).isNotNull();
    }

    @Test
    public void testLoadUserByUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> sut.loadUserByUsername("test"));
    }

}
