package softuni.photostore.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserEditBindingModel {

  private String id;

  private String username;

  @NotNull
  @Size(min=4, max=30)
  private String firstName;

  @NotNull
  @Size(min=4, max=30)
  private String lastName;

  @NotBlank
  @Size(min=4, max=30)
  private String password;

  @NotBlank
  @Email
  private String email;

  private boolean isAdmin;

  public String getId() {
    return id;
  }

  public UserEditBindingModel setId(String id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserEditBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserEditBindingModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserEditBindingModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserEditBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserEditBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public UserEditBindingModel setAdmin(boolean admin) {
    isAdmin = admin;
    return this;
  }
}
