package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.validator.UniqueEmailValidator;
import softuni.photostore.model.validator.UniqueUserNameValidator;
import softuni.photostore.repository.UsersRepository;
import softuni.photostore.service.UsersService;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ValidatorsTest {

    private UniqueEmailValidator mailValidator;
    private UniqueUserNameValidator usernameValidator;
    @Mock
    private UsersService mockedUserService;

    @BeforeEach
    public void setup() {
        mailValidator = new UniqueEmailValidator(mockedUserService);
        usernameValidator = new UniqueUserNameValidator(mockedUserService);
    }

    @Test
    public void testIsEmailNotUnique() {
        Mockito.when(mockedUserService.isEmailFree("mail"))
                        .thenReturn(false);
        assertThat(mailValidator.isValid("mail", Mockito.mock(ConstraintValidatorContext.class))).isFalse();
    }

    @Test
    public void testIsEmailUnique() {
        assertThat(mailValidator.isValid(null, Mockito.mock(ConstraintValidatorContext.class))).isTrue();
    }

    @Test
    public void testIsUsernameNotUnique() {
        Mockito.when(mockedUserService.isUserNameFree("test"))
                        .thenReturn(false);
        assertThat(usernameValidator.isValid("test", Mockito.mock(ConstraintValidatorContext.class))).isFalse();
    }

    @Test
    public void testIsUsernameUnique() {
        assertThat(usernameValidator.isValid(null, Mockito.mock(ConstraintValidatorContext.class))).isTrue();
    }
}
