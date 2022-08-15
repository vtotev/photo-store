package softuni.photostore.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.photostore.model.entity.CartItem;
import softuni.photostore.model.view.CartItemListView;
import softuni.photostore.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String openShoppingCart() {
        return "shopping";
    }

    @PostMapping("/cart/remove/{id}")
    public void removeItemFromCart(@PathVariable("id") String productId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cartService.removeItemFromCartById(productId);
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(request.getHeader("referer"));
    }

    @PostMapping("/cart/clear")
    public String clearCurrentCart(Model model) {
        List<CartItemListView> cartItems = (List<CartItemListView>) model.getAttribute("cartItems");
        if (cartItems != null && !cartItems.isEmpty()) {
            cartItems.forEach(c -> cartService.removeItemFromCartById(c.getId()));
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/inc/{id}")
    public String increaseItemQuantity(@PathVariable("id") String productId) {
        cartService.incQuantity(productId, 1);
        return "redirect:/cart";
    }

    @PostMapping("/cart/dec/{id}")
    public String decreaseItemQuantity(@PathVariable("id") String productId) {
        cartService.decQuantity(productId, 1);
        return "redirect:/cart";
    }

}
