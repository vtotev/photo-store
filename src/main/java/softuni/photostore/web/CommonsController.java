package softuni.photostore.web;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.view.CartItemListView;
import softuni.photostore.service.*;

import java.math.BigDecimal;
import java.util.List;

@ControllerAdvice
public class CommonsController {

    private final CameraService cameraService;
    private final UsersService usersService;
    private final CartService cartService;

    private final CommonsService commonsService;

    private final StatsService statsService;

    public CommonsController(CameraService cameraService, UsersService usersService, CartService cartService, CommonsService commonsService, StatsService statsService) {
        this.cameraService = cameraService;
        this.usersService = usersService;
        this.cartService = cartService;
        this.commonsService = commonsService;
        this.statsService = statsService;
    }


    @ModelAttribute
    public void getBrands(Model model) {
        model.addAttribute("cameraBrands", cameraService.getAllBrands());
    }

    @ModelAttribute
    public void getStats(Model model) {
        model.addAttribute("stats", statsService.getStats());
    }

    @ModelAttribute
    public void getCartItems(@CurrentSecurityContext SecurityContext context, Model model) {
        Object[] userData = commonsService.getSessionUserInfo(context);
        User user = (User) userData[0];
        String remoteIP = (String) userData[1];
        List<CartItemListView> cartItems = cartService.getCartForCurrentUserOrIP(user, remoteIP);
        double cartSum = cartItems.stream().mapToDouble(cartItemListView -> {
            return cartItemListView.getQuantity() * cartItemListView.getPrice().doubleValue();
        }).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalCartSum", cartSum);
    }


}
