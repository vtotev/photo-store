package softuni.photostore.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.photostore.model.binding.UserRegistrationBindingModel;
import softuni.photostore.model.service.UserRegistrationServiceModel;
import softuni.photostore.service.UsersService;

import javax.validation.Valid;

@Controller
public class UserRegistrationController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public UserRegistrationController(UsersService usersService,
                                      ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userModel")
    public UserRegistrationBindingModel userModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/users/register")
    public String registerUser() {
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String register(
            @Valid UserRegistrationBindingModel userModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userModel.getPassword().equals(userModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:/users/register";
        }

        UserRegistrationServiceModel serviceModel =
                modelMapper.map(userModel, UserRegistrationServiceModel.class);

        usersService.registerAndLoginUser(serviceModel);

        return "redirect:/";
    }

}
