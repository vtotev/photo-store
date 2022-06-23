package softuni.photostore.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserEditByAdminBindingModel {

  private String id;

  private String username;

  @NotNull
  @Size(min=4, max=30)
  private String firstName;

  @NotNull
  @Size(min=4, max=30)
  private String lastName;

  @NotBlank
  @Email
  private String email;

  private boolean isAdmin;

  public String getId() {
    return id;
  }

  public UserEditByAdminBindingModel setId(String id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserEditByAdminBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserEditByAdminBindingModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserEditByAdminBindingModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserEditByAdminBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public UserEditByAdminBindingModel setAdmin(boolean admin) {
    isAdmin = admin;
    return this;
  }
}
