package softuni.photostore.service;

import softuni.photostore.model.binding.UserChangePwdBindingModel;
import softuni.photostore.model.binding.UserEditBindingModel;
import softuni.photostore.model.binding.UserEditByAdminBindingModel;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.service.UserRegistrationServiceModel;
import softuni.photostore.model.view.UserMngViewModel;

import java.util.List;

public interface UsersService {
    void initializeRoles();

    boolean isUserNameFree(String userName);

    boolean isEmailFree(String email);

    boolean isUserAdmin(String id);

    void registerAndLoginUser(UserRegistrationServiceModel userRegSvcModel);

    void loginNewUser(User newUser);

    void initializeUsers();

    List<UserMngViewModel> getAllUsersForManagement();

    UserEditBindingModel getUserToEditById(String id);

    UserEditByAdminBindingModel getUserToEditByAdminId(String id);

    boolean editUserData(UserEditBindingModel userModel);

    boolean isPasswordCorrect(String password, String userId);

    boolean isThereMoreThanOneActiveAdmin();

    User getUserByUsername(String currUser);

    User getUserById(String userId);

    boolean resetPasswordForUserWithId(String id);

    boolean changeUserPassword(UserChangePwdBindingModel userModel);

}
