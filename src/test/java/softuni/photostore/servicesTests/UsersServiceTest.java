package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.photostore.model.binding.*;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.entity.accounts.UserRole;
import softuni.photostore.model.entity.enums.UserRoleEnum;
import softuni.photostore.model.service.UserRegistrationServiceModel;
import softuni.photostore.model.view.UserMngViewModel;
import softuni.photostore.repository.UserRoleRepository;
import softuni.photostore.repository.UsersRepository;
import softuni.photostore.service.UsersService;
import softuni.photostore.service.impl.AppUser;
import softuni.photostore.service.impl.AppUserServiceImpl;
import softuni.photostore.service.impl.UsersServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    private UsersService sut;
    @Mock
    private UserRoleRepository mockedRole;
    @Mock
    private UsersRepository mockedUsers;

    private PasswordEncoder passEncoder;
    @Mock
    private AppUserServiceImpl mockedAppUserService;
    @Mock
    private ModelMapper mockedMapper;
    private UserRole adminRole;
    private UserRole userRole;

    @BeforeEach
    public void setup() {
        adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        passEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString().repeat(3);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
        this.sut = new UsersServiceImpl(mockedRole, mockedUsers, passEncoder, mockedAppUserService, mockedMapper);
    }

    @Test
    public void testInitializeRoles() {
        Mockito.when(mockedRole.count())
                .thenReturn(0L);
        sut.initializeRoles();
        Mockito.verify(mockedRole, Mockito.times(1)).count();
    }

    @Test
    public void testInitializeRolesAlreadyDone() {
        Mockito.when(mockedRole.count())
                .thenReturn(2l);
        sut.initializeRoles();
        Mockito.verify(mockedRole, Mockito.times(1)).count();
    }

    @Test
    public void testInitializeUsers() {
        Mockito.when(mockedRole.findByRole(UserRoleEnum.ADMIN))
                .thenReturn(adminRole);
        Mockito.when(mockedRole.findByRole(UserRoleEnum.USER))
                .thenReturn(userRole);

        Mockito.when(mockedUsers.count())
                .thenReturn(0L);
        sut.initializeUsers();
        Mockito.verify(mockedUsers, Mockito.times(1)).count();
    }

    @Test
    public void testInitializeUsersAlreadyDone() {
        Mockito.when(mockedUsers.count())
                .thenReturn(2l);
        sut.initializeUsers();
        Mockito.verify(mockedUsers, Mockito.times(1)).count();
    }


    @Test
    public void testIsUsernameTaken() {
        Mockito.when(mockedUsers.findByUsernameIgnoreCase("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(User.class)));
        assertThat(sut.isUserNameFree("test")).isFalse();
    }

    @Test
    public void testIsUsernameFree() {
        assertThat(sut.isUserNameFree("test")).isTrue();
    }

    @Test
    public void testIsEmailTaken() {
        Mockito.when(mockedUsers.findByEmailIgnoreCase("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(User.class)));
        assertThat(sut.isEmailFree("test")).isFalse();
    }

    @Test
    public void testIsEmailFree() {
        assertThat(sut.isEmailFree("test")).isTrue();
    }

    @Test
    public void testIsUserAdmin() {
        User user = new User();
        user.setRoles(Set.of(adminRole));
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.ofNullable(user));
        assertThat(sut.isUserAdmin("test")).isTrue();
    }

    @Test
    public void testIsUserNotAdmin() {
        assertThat(sut.isUserAdmin("test")).isFalse();
    }

    @Test
    public void testRegisterNewUser() {
        UserRegistrationServiceModel user = new UserRegistrationServiceModel();
        user.setUsername("test")
                .setEmail("test@test.com")
                .setPassword("test")
                .setFirstName("test")
                .setLastName("test");


        Mockito.when(mockedRole.findByRole(UserRoleEnum.USER))
                .thenReturn(userRole);

        User newUser = new User();
        newUser
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setActive(true)
                .setPassword("test")
                .setEmail(user.getEmail())
                .setRoles(Set.of(userRole));

        UserDetails ud = new AppUser("test", "test", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        Mockito.when(mockedUsers.save(Mockito.any()))
                .thenReturn(newUser);
        Mockito.when(mockedAppUserService.loadUserByUsername(user.getUsername()))
                .thenReturn(ud);
        sut.registerAndLoginUser(user);
    }

    @Test
    public void testGetAllUsersForManagement() {
        UserMngViewModel user = new UserMngViewModel();
        Random random = new Random();
        int i = random.nextInt();
//        int i = RandomGenerator.getDefaul().nextInt(5);
        user.setUsername("user")
                .setFirstName("first")
                .setLastName("last")
                .setAdmin(true)
                .setId("test" + i);

        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getId()).isEqualTo("test"+i);
        assertThat(user.getFirstName()).isEqualTo("first");
        assertThat(user.getLastName()).isEqualTo("last");
        assertThat(user.isAdmin()).isTrue();

        Mockito.when(mockedUsers.findAll())
                .thenReturn(List.of(new User(), new User()));
        Mockito.when(mockedMapper.map(Mockito.any(), Mockito.eq(UserMngViewModel.class)))
                .thenReturn(user);
        List<UserMngViewModel> testUsers = sut.getAllUsersForManagement();
        assertThat(testUsers).isNotNull();
        assertThat(testUsers).size().isEqualTo(2);
        assertThat(testUsers.get(0).getUsername()).isEqualTo("user");
    }

    @Test
    public void testGetUserToEditById() {
        User usr = new User();
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(usr));
        Mockito.when(mockedMapper.map(Mockito.any(), Mockito.eq(UserEditBindingModel.class)))
                .thenReturn(new UserEditBindingModel());
        UserEditBindingModel userToEdit = sut.getUserToEditById("test");
        assertThat(userToEdit).isNotNull();
        assertThat(userToEdit).isInstanceOf(UserEditBindingModel.class);
    }

    @Test
    public void testGetUserToEditByIdUserNotFound() {
        UserEditBindingModel userToEdit = sut.getUserToEditById("test");
        assertThat(userToEdit).isNull();
    }

    @Test
    public void testGetUserToEditByAdminId() {
        User usr = new User();
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(usr));
        Mockito.when(mockedMapper.map(Mockito.any(), Mockito.eq(UserEditByAdminBindingModel.class)))
                .thenReturn(new UserEditByAdminBindingModel());
        UserEditByAdminBindingModel userToEdit = sut.getUserToEditByAdminId("test");
        assertThat(userToEdit).isNotNull();
        assertThat(userToEdit).isInstanceOf(UserEditByAdminBindingModel.class);
    }

    @Test
    public void testGetUserToEditByAdminIdNotFound() {
        UserEditByAdminBindingModel userToEdit = sut.getUserToEditByAdminId("test");
        assertThat(userToEdit).isNull();
    }

    @Test
    public void testIsPasswordCorrect() {
        User usr = new User();
        usr.setPassword(passEncoder.encode("test"));
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(usr));
        boolean test = sut.isPasswordCorrect("test", "test");
        assertThat(test).isTrue();
    }

    @Test
    public void testGetUserByUsername() {
        User testUser = new User();
        testUser.setUsername("user")
                .setPassword("pass")
                .setEmail("mail")
                .setFirstName("first")
                .setLastName("name")
                .setRoles(Set.of(adminRole))
                .setActive(true)
                .setId("testId");

        assertThat(testUser.getUsername()).isEqualTo("user");
        assertThat(testUser.getPassword()).isEqualTo("pass");
        assertThat(testUser.getEmail()).isEqualTo("mail");
        assertThat(testUser.getFirstName()).isEqualTo("first");
        assertThat(testUser.getLastName()).isEqualTo("name");
        assertThat(testUser.getId()).isEqualTo("testId");
        assertThat(testUser.isActive()).isTrue();
        assertThat(testUser.getRoles()).anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN));

        Mockito.when(mockedUsers.findByUsernameIgnoreCase("test"))
                .thenReturn(Optional.of(new User()));
        User usr = sut.getUserByUsername("test");
        assertThat(usr).isNotNull();
        assertThat(usr).isInstanceOf(User.class);
    }

    @Test
    public void testGetUserByUsernameNotFound() {
        User usr = sut.getUserByUsername("test");
        assertThat(usr).isNull();
    }

    @Test
    public void testIsThereMoreThanOneActiveAdmin() {
        Mockito.when(mockedUsers.findAllAdmins())
                .thenReturn(List.of(new User(), new User()));
        assertThat(sut.isThereMoreThanOneActiveAdmin()).isTrue();
    }

    @Test
    public void testIsThereMoreThanOneActiveAdminNotTrue() {
        Mockito.when(mockedUsers.findAllAdmins())
                .thenReturn(List.of(new User()));
        assertThat(sut.isThereMoreThanOneActiveAdmin()).isFalse();
    }

    @Test
    public void testResetPasswordForUserWithId() {
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(new User()));
        assertThat(sut.resetPasswordForUserWithId("test")).isTrue();
    }

    @Test
    public void testResetPasswordForUserWithIdNotFound() {
        assertThat(sut.resetPasswordForUserWithId("test")).isFalse();
    }

    @Test
    public void testChangeUserPassword() {
        UserChangePwdBindingModel usr = new UserChangePwdBindingModel();
        usr.setId("test")
                .setOldPassword("test")
                .setPassword("test")
                .setConfirmPassword("test");

        assertThat(usr.getId()).isEqualTo("test");
        assertThat(usr.getOldPassword()).isEqualTo("test");
        assertThat(usr.getPassword()).isEqualTo("test");
        assertThat(usr.getConfirmPassword()).isEqualTo("test");

        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(new User()));
        assertThat(sut.changeUserPassword(usr)).isTrue();
    }

    @Test
    public void testChangeUserPasswordFailedUserNotFound() {
        UserChangePwdBindingModel usr = new UserChangePwdBindingModel();
        usr.setId("test")
                .setOldPassword("test")
                .setPassword("test")
                .setConfirmPassword("test");
        assertThat(sut.changeUserPassword(usr)).isFalse();
    }

    @Test
    public void testEditUserDataAndSetAdminRole() {
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole);

        User usr = new User();
        usr
                .setUsername("test")
                .setFirstName("test")
                .setLastName("test")
                .setActive(true)
                .setPassword("test")
                .setEmail("test@test.com")
                .setRoles(roles);
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(usr));

        UserEditBindingModel usrToEdit = new UserEditBindingModel();
        usrToEdit.setId("test")
                .setUsername("test")
                .setFirstName("test")
                .setLastName("test")
                .setPassword("test")
                .setEmail("test@test.com")
                .setAdmin(true);
        assertThat(sut.editUserData(usrToEdit)).isTrue();
    }

    @Test
    public void testEditUserDataAndSetUserRole() {
        Set<UserRole> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);

        User usr = new User();
        usr
                .setUsername("test")
                .setFirstName("test")
                .setLastName("test")
                .setActive(true)
                .setPassword("test")
                .setEmail("test@test.com")
                .setRoles(roles);
        Mockito.when(mockedUsers.findById("test"))
                .thenReturn(Optional.of(usr));

        UserEditBindingModel usrToEdit = new UserEditBindingModel();
        usrToEdit.setId("test")
                .setUsername("test")
                .setFirstName("test")
                .setLastName("test")
                .setPassword("test")
                .setEmail("test@test.com")
                .setAdmin(false);
        assertThat(usrToEdit.getUsername()).isEqualTo("test");
        assertThat(usrToEdit.getPassword()).isEqualTo("test");
        assertThat(sut.editUserData(usrToEdit)).isTrue();
    }

    @Test
    public void testEditUserDataWithNotExistingUser() {
        assertThat(sut.editUserData(new UserEditBindingModel())).isFalse();
    }

    @Test
    public void testLoginBindingModel() {
        LoginBindingModel login = new LoginBindingModel();
        login.setEmail("test@test.com")
                .setPassword("test");
        assertThat(login.getEmail()).isEqualTo("test@test.com");
        assertThat(login.getPassword()).isEqualTo("test");
    }

    @Test
    public void testUserRegistrationBindingModel() {
        UserRegistrationBindingModel registation = new UserRegistrationBindingModel();
        registation.setEmail("test@test.com")
                .setPassword("test")
                .setConfirmPassword("test")
                .setFirstName("first")
                .setLastName("name")
                .setUsername("user");
        assertThat(registation.getEmail()).isEqualTo("test@test.com");
        assertThat(registation.getUsername()).isEqualTo("user");
        assertThat(registation.getFirstName()).isEqualTo("first");
        assertThat(registation.getLastName()).isEqualTo("name");
        assertThat(registation.getPassword()).isEqualTo("test");
        assertThat(registation.getConfirmPassword()).isEqualTo("test");
    }

    @Test
    public void testUserEditByAdminBindingModel() {
        UserEditByAdminBindingModel registation = new UserEditByAdminBindingModel();
        registation.setEmail("test@test.com")
                .setId("test")
                .setAdmin(true)
                .setFirstName("first")
                .setLastName("name")
                .setUsername("user");
        assertThat(registation.getEmail()).isEqualTo("test@test.com");
        assertThat(registation.getId()).isEqualTo("test");
        assertThat(registation.getUsername()).isEqualTo("user");
        assertThat(registation.getFirstName()).isEqualTo("first");
        assertThat(registation.getLastName()).isEqualTo("name");
        assertThat(registation.isAdmin()).isTrue();
    }
}
