package softuni.photostore.web;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.CartService;
import softuni.photostore.service.UsersService;

import java.util.ArrayList;

@ControllerAdvice
public class CommonsController {

    private final CameraService cameraService;
    private final UsersService usersService;
    private final CartService cartService;

    public CommonsController(CameraService cameraService, UsersService usersService, CartService cartService) {
        this.cameraService = cameraService;
        this.usersService = usersService;
        this.cartService = cartService;
    }


    @ModelAttribute
    public void getBrands(Model model) {
        model.addAttribute("cameraBrands", cameraService.getAllBrands());
    }

    @ModelAttribute
    public void getCartItems(@CurrentSecurityContext SecurityContext context, Model model) {
        User user = null;
        String remoteIP = "";
        Authentication authentication = context.getAuthentication();
        if (authentication != null) {
            AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
            if (!authenticationTrustResolver.isAnonymous(authentication)) {
                user = usersService.getUserByUsername(authentication.getName());
            }
            if (context.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails authDetails = (WebAuthenticationDetails) context.getAuthentication().getDetails();
                remoteIP = authDetails.getRemoteAddress();
            }
        }
        model.addAttribute("cartItems", cartService.getCartForCurrentUserOrIP(user, remoteIP));
    }
}
