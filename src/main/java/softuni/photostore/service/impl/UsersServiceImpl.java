package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.photostore.model.binding.UserChangePwdBindingModel;
import softuni.photostore.model.binding.UserEditBindingModel;
import softuni.photostore.model.binding.UserEditByAdminBindingModel;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.entity.accounts.UserRole;
import softuni.photostore.model.entity.enums.UserRoleEnum;
import softuni.photostore.model.service.UserRegistrationServiceModel;
import softuni.photostore.model.view.UserMngViewModel;
import softuni.photostore.repository.UserRoleRepository;
import softuni.photostore.repository.UsersRepository;
import softuni.photostore.service.UsersService;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRoleRepository userRoleRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserServiceImpl appUserService;
    private final ModelMapper modelMapper;

    public UsersServiceImpl(UserRoleRepository userRoleRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder, AppUserServiceImpl appUserService, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole();
            adminRole.setRole(UserRoleEnum.ADMIN);

            UserRole userRole = new UserRole();
            userRole.setRole(UserRoleEnum.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    @Override
    public boolean isUserNameFree(String userName) {
        return usersRepository.findByUsernameIgnoreCase(userName).isEmpty();
    }

    @Override
    public boolean isEmailFree(String email) {
        return usersRepository.findByEmailIgnoreCase(email).isEmpty();
    }

    @Override
    public boolean isUserAdmin(String id) {
        User user = usersRepository.findById(id).orElse(null);
        if (user != null && user.getRoles().stream().filter(userRole -> userRole.getRole().equals(UserRoleEnum.ADMIN)).findAny().orElse(null) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel userRegSvcModel) {
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        User newUser = new User();

        newUser
                .setUsername(userRegSvcModel.getUsername())
                .setFirstName(userRegSvcModel.getFirstName())
                .setLastName(userRegSvcModel.getLastName())
                .setActive(true)
                .setPassword(passwordEncoder.encode(userRegSvcModel.getPassword()))
                .setEmail(userRegSvcModel.getEmail())
                .setRoles(Set.of(userRole));

        newUser = usersRepository.save(newUser);
        loginNewUser(newUser);
    }

    @Override
    public void loginNewUser(User newUser) {
        UserDetails principal = appUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);
    }

    @Override
    public void initializeUsers() {
        if (usersRepository.count() == 0) {
            User admin = new User()
                    .setFirstName("Valeri")
                    .setLastName("Totev")
                    .setActive(true)
                    .setEmail("mail@mail.bg")
                    .setUsername("admin")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(Set.of(
                            userRoleRepository.findByRole(UserRoleEnum.ADMIN),
                            userRoleRepository.findByRole(UserRoleEnum.USER)));
            usersRepository.save(admin);
        }
    }

    @Override
    public List<UserMngViewModel> getAllUsersForManagement() {
        return usersRepository.findAll().stream()
                .map(user -> {
                    UserMngViewModel map = modelMapper.map(user, UserMngViewModel.class);
                    map.setAdmin(isUserAdmin(map.getId()));
                    return map;
                })
                .sorted((o1, o2) -> {
                    int compare = Boolean.compare(o2.isAdmin(), o1.isAdmin());
                    if (compare == 0) {
                        return o1.getUsername().compareTo(o2.getUsername());
                    }
                    return compare;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserEditBindingModel getUserToEditById(String id) {
        return usersRepository.findById(id).map(user -> modelMapper.map(user, UserEditBindingModel.class)).orElse(null);
    }

    @Override
    public UserEditByAdminBindingModel getUserToEditByAdminId(String id) {
        return usersRepository.findById(id).map(user -> modelMapper.map(user, UserEditByAdminBindingModel.class)).orElse(null);
    }

    @Override
    public boolean editUserData(UserEditBindingModel userModel) {
        User userToEdit = usersRepository.findById(userModel.getId()).orElse(null);
        if (userToEdit == null) {
            return false;
        }
        userToEdit
                .setUsername(userModel.getUsername())
                .setFirstName(userModel.getFirstName())
                .setLastName(userModel.getLastName())
                .setEmail(userModel.getEmail());

        if (userModel.isAdmin() && !isUserAdmin(userToEdit.getId())) {
            userToEdit.getRoles().add(userRoleRepository.findByRole(UserRoleEnum.ADMIN));
        } else if (!userModel.isAdmin() && isUserAdmin(userToEdit.getId()) && isThereMoreThanOneActiveAdmin()) {
            userToEdit.getRoles().removeIf(userRole -> userRole.getRole().equals(UserRoleEnum.ADMIN));
        }
        usersRepository.save(userToEdit);
        return true;
    }

    @Override
    public boolean isPasswordCorrect(String password, String userId) {
        return passwordEncoder.matches(password, Objects.requireNonNull(usersRepository.findById(userId).orElse(null)).getPassword());
    }

    @Override
    public boolean isThereMoreThanOneActiveAdmin() {
        return usersRepository.findAllAdmins().size() > 1;
    }

    @Override
    public User getUserByUsername(String currUser) {
        return usersRepository.findByUsernameIgnoreCase(currUser).orElse(null);
    }

    @Override
    public User getUserById(String userId) {
        return usersRepository.getById(userId);
    }

    @Override
    public boolean resetPasswordForUserWithId(String id) {
        User user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode("1234"));
        usersRepository.save(user);
        return true;
    }

    @Override
    public boolean changeUserPassword(UserChangePwdBindingModel userModel) {
        User user = usersRepository.findById(userModel.getId()).orElse(null);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        usersRepository.save(user);
        return true;
    }
}
