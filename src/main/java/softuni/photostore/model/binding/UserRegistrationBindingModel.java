package softuni.photostore.model.binding;

import softuni.photostore.model.validator.UniqueEmail;
import softuni.photostore.model.validator.UniqueUserName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserRegistrationBindingModel {

  @NotNull
  @Size(min=4, max=30)
  private String firstName;

  @NotNull
  @Size(min=4, max=30)
  private String lastName;

  @NotNull
  @Size(min=4, max=30)
  private String password;

  @NotNull
  @Size(min=4, max=30)
  private String confirmPassword;

  @NotBlank(message = "Потребителското име е задължително")
  @Size(min=4, max=20, message = "Потребителското име трябва да е между 4 и 30 символа")
  @UniqueUserName
  private String username;

  @NotBlank
  @Email
  @UniqueEmail
  private String email;

  public String getUsername() {
    return username;
  }

  public UserRegistrationBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserRegistrationBindingModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserRegistrationBindingModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserRegistrationBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserRegistrationBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }
}
