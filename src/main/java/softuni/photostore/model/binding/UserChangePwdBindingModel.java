package softuni.photostore.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserChangePwdBindingModel {

  private String id;

  @NotNull
  @Size(min=4)
  private String oldPassword;

  @NotNull
  @Size(min=4)
  private String password;

  @NotNull
  @Size(min=4)
  private String confirmPassword;

  public String getId() {
    return id;
  }

  public UserChangePwdBindingModel setId(String id) {
    this.id = id;
    return this;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public UserChangePwdBindingModel setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserChangePwdBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserChangePwdBindingModel setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }
}
