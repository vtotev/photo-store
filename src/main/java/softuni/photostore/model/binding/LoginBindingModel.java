package softuni.photostore.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginBindingModel {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 3)
    private String password;

    public LoginBindingModel() {
    }

    public String getEmail() {
        return email;
    }

    public LoginBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
