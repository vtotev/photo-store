package softuni.photostore.model.view;

import softuni.photostore.model.entity.accounts.UserRole;

import java.util.stream.Stream;

public class UserMngViewModel {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isAdmin;

    public UserMngViewModel() {
    }

    public String getId() {
        return id;
    }

    public UserMngViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserMngViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserMngViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserMngViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public UserMngViewModel setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}
