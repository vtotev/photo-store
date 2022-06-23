package softuni.photostore.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements UserDetailsService {

  private final UsersRepository usersRepository;

  public AppUserServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // The purpose of this method is to map our user representation (UserEntity)
    // to the user representation in the spring sercurity world (UserDetails).
    // The only thing that spring will provide to us is the user name.
    // The user name will come from the HTML login form.

    User userEntity =
        usersRepository.findByUsernameIgnoreCase(username).
            orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));

    return mapToUserDetails(userEntity);
  }

  private static UserDetails mapToUserDetails(User user) {

    // GrantedAuthority is the representation of a user role in the
    // spring world. SimpleGrantedAuthority is an implementation of GrantedAuthority
    // which spring provides for our convenience.
    // Our representation of role is UserRoleEntity
    List<GrantedAuthority> auhtorities =
        user.
            getRoles().
            stream().
            map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).
            collect(Collectors.toList());

    // User is the spring implementation of UserDetails interface.
    return new AppUser(
        user.getUsername(),
        user.getPassword(),
        auhtorities
    );
  }
}
