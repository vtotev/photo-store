package softuni.photostore.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.photostore.model.binding.UserChangePwdBindingModel;
import softuni.photostore.model.binding.UserEditBindingModel;
import softuni.photostore.model.binding.UserEditByAdminBindingModel;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.service.UsersService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserEditController {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserEditController(UsersService usersService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    // USERS EDIT PANEL FOR ADMINISTRATORS ONLY

    @GetMapping("/manage/users")
    public String manageAccounts(Model model) {
        model.addAttribute("accounts", usersService.getAllUsersForManagement());
        if (!model.containsAttribute("successfulPasswordReset")) {
            model.addAttribute("successfulPasswordReset", false);
        }
        return "accounts-manage";
    }

    @GetMapping("/manage/users/edit/{id}")
    public String editAccountFromAdmin(@PathVariable String id, Model model) {
        if (!model.containsAttribute("userModel")) {
            UserEditByAdminBindingModel userToEditById = usersService.getUserToEditByAdminId(id);
            userToEditById.setAdmin(usersService.isUserAdmin(id));
            model.addAttribute("userModel", userToEditById);
        }
        return "auth-edit";
    }

    @PostMapping("/manage/users/edit/{id}")
    public String editAccountFromAdminConfirm(
            @PathVariable String id,
            @Valid UserEditByAdminBindingModel userModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || (!usersService.isEmailFree(userModel.getEmail())
                && !usersService.getUserToEditById(id).getEmail().equals(userModel.getEmail()))) {
            UserEditByAdminBindingModel userToEdit = usersService.getUserToEditByAdminId(id);
            userModel.setUsername(userToEdit.getUsername())
                    .setId(userToEdit.getId());
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/manage/users/edit/" + userModel.getId();
        }
        usersService.editUserData(modelMapper.map(userModel, UserEditBindingModel.class));
        return "redirect:/manage/users";
    }

    @PostMapping("/manage/users/passreset/{id}")
    public String passwordResetFromAdmin(@PathVariable String id, RedirectAttributes redirectAttributes) {
        boolean successful = usersService.resetPasswordForUserWithId(id);
        redirectAttributes.addFlashAttribute("successfulPasswordReset", successful);
        return "redirect:/manage/users";
    }

    // CURRENT LOGGED USER EDIT
    @GetMapping("/manage/user/edit")
    public String editAccount(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User userToEdit = usersService.getUserByUsername(currentUser.getUsername());
        if (userToEdit != null) {
            if (!model.containsAttribute("userModel")) {
                UserEditBindingModel userToEditById = usersService.getUserToEditById(userToEdit.getId());
                model.addAttribute("userModel", userToEditById);
            }
            return "auth-edit-currentuser";
        }
        return "redirect:/";
    }

    @PostMapping("/manage/user/edit")
    public String edit_AccountConfirm(
            @Valid UserEditBindingModel userModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails currentUser) {

        User currUser = usersService.getUserByUsername(currentUser.getUsername());
        if (currUser == null) {
            return "redirect:/";
        }
        userModel.setId(currUser.getId());
        if (bindingResult.hasErrors()
//                || (!userModel.getUsername().equalsIgnoreCase(currUser.getUsername()) && usersService.isUserNameFree(userModel.getUsername()))
                || !usersService.isPasswordCorrect(userModel.getPassword(), currUser.getId())
                || (!usersService.isEmailFree(userModel.getEmail())
                && !usersService.getUserToEditById(currUser.getId()).getEmail().equals(userModel.getEmail()))) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/manage/user/edit";
        }
        usersService.editUserData(userModel);
        usersService.loginNewUser(usersService.getUserByUsername(userModel.getUsername()));
        return "redirect:/";
    }

    // USER CHANGE PASSWORD
    @GetMapping("/manage/user/changepassword")
    public String changePassword(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User userToEdit = usersService.getUserByUsername(currentUser.getUsername());
        if (userToEdit != null) {
            if (!model.containsAttribute("userModel")) {
                model.addAttribute("userModel", new UserChangePwdBindingModel()
                                .setId(userToEdit.getId()))
                        .addAttribute("oldNotMatch", false)
                        .addAttribute("passMismatches", false);
            }
            return "auth-change-password";
        }
        return "redirect:/";
    }

    @PostMapping("/manage/user/changepassword")
    public String changePasswordConfirm(
            @Valid UserChangePwdBindingModel userModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails currentUser) {

        User currUser = usersService.getUserByUsername(currentUser.getUsername());
        if (currUser == null) {
            return "redirect:/";
        }

        boolean isOldCorrect = usersService.isPasswordCorrect(userModel.getOldPassword(), currUser.getId());
        boolean areNewMatching = userModel.getPassword().equals(userModel.getConfirmPassword());

        userModel.setId(currUser.getId());
        if (bindingResult.hasErrors() || !isOldCorrect || !areNewMatching) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("oldNotMatch", !isOldCorrect)
                    .addFlashAttribute("passMismatches", !areNewMatching);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/manage/user/changepassword";
        }
        usersService.changeUserPassword(userModel);
        return "redirect:/";
    }
}
