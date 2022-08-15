package softuni.photostore.service;

import org.springframework.security.core.context.SecurityContext;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.CartItem;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.view.CartItemListView;

import java.time.LocalDateTime;
import java.util.List;

public interface CartService {

    CartItem addItemToCart(BaseModel product, SecurityContext context, Class productClass);

    void removeItemFromCartById(String itemId);

    List<CartItemListView> getCartForCurrentUserOrIP(User user, String remoteIP);

    void cleanCart(LocalDateTime dateTime);

    CartItem getCartItemById(String productId);

    void incQuantity(String productId, Integer incWith);

    void decQuantity(String productId, Integer decWith);

}
