package softuni.photostore.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUser extends User {


  public AppUser(String username, String password,
                 Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public AppUser(String username, String password, boolean enabled, boolean accountNonExpired,
                 boolean credentialsNonExpired, boolean accountNonLocked,
                 Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
  }

  // some own methods below.

  public String getUserIdentifier() {
    return getUsername();
  }
}
