package softuni.photostore.model.validator;

import softuni.photostore.service.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

  private final UsersService usersService;

  public UniqueUserNameValidator(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public boolean isValid(String userName, ConstraintValidatorContext context) {
    if (userName == null) {
      return true;
    }
    return usersService.isUserNameFree(userName);
  }
}
