package softuni.photostore.service;

import org.springframework.security.core.context.SecurityContext;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.CartItem;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.view.CartItemListView;

import java.time.LocalDateTime;
import java.util.List;

public interface CartService {

    //    CartItem addItemToCart(String customerId, String remoteIP, BaseModel product, Integer quantity, String productType);
    CartItem addItemToCart(BaseModel product, SecurityContext context, Class productClass);

    CartItem updateItemQuantity(CartItem item, Integer quantity);

    void removeItemFromCartById(String itemId);

    List<CartItemListView> getCartForCurrentUserOrIP(User user, String remoteIP);

    void cleanCart(LocalDateTime dateTime);
}
