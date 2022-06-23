package softuni.photostore.model.entity.accounts;

import softuni.photostore.model.entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private boolean isActive;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<UserRole> roles = new HashSet<>();

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public User setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public User setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public boolean isActive() {
    return isActive;
  }

  public User setActive(boolean active) {
    isActive = active;
    return this;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }

  public User setRoles(
      Set<UserRole> roles) {
    this.roles = roles;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }


}
