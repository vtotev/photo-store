package softuni.photostore.model.validator;

import softuni.photostore.service.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UsersService usersService;

  public UniqueEmailValidator(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null) {
      return true;
    }
    return usersService.isEmailFree(email);
  }
}
