package softuni.photostore.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.photostore.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/remove/{id}")
    public void removeItemFromCart(@PathVariable("id") String productId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cartService.removeItemFromCartById(productId);
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(request.getHeader("referer"));
    }

}
