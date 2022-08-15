package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.CartItem;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.view.CartItemListView;
import softuni.photostore.repository.CartRepository;
import softuni.photostore.service.CartService;
import softuni.photostore.service.UsersService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final UsersService usersService;

    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper, UsersService usersService) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
    }

    @Override
    public CartItem addItemToCart(BaseModel product, SecurityContext context, Class productClass) {
        User user = null;
        String remoteIP = "";
        Authentication authentication = context.getAuthentication();
        AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
        if (!authenticationTrustResolver.isAnonymous(authentication)) {
            user = usersService.getUserByUsername(authentication.getName());
        }
        if (context.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails authDetails = (WebAuthenticationDetails) context.getAuthentication().getDetails();
            remoteIP = authDetails.getRemoteAddress();
        }

        String customer = user != null ? user.getId() : context.getAuthentication().getName();

        CartItem item = cartRepository.getCartItemByCustomerIdAndCustomerIPAndProductId(customer, remoteIP, product.getId())
                .orElse(null);

        if (item == null) {
            item = new CartItem()
                    .setCustomerId(customer)
                    .setCustomerIP(remoteIP)
                    .setProductName(product.getModelName())
                    .setProductId(product.getId())
                    .setQuantity(1)
                    .setPrice(product.getPrice())
                    .setProductType(productClass.getSimpleName())
                    .setPictureUrl(product.getPictures().getUrl())
                    .setDateAdded(LocalDateTime.now());
        } else {
            item
                    .setQuantity(item.getQuantity() + 1)
                    .setDateAdded(LocalDateTime.now());
        }
        return cartRepository.save(item);
    }

    @Override
    public void removeItemFromCartById(String itemId) {
        cartRepository.deleteById(itemId);
    }

    @Override
    public List<CartItemListView> getCartForCurrentUserOrIP(User user, String remoteIP) {
        if (user != null) {
            return cartRepository
                    .getAllByCustomerId(user.getId()).stream().map(c -> modelMapper.map(c, CartItemListView.class))
                    .collect(Collectors.toList());
        }
        return cartRepository.getAllByCustomerIP(remoteIP).stream().map(c -> modelMapper.map(c, CartItemListView.class))
                .collect(Collectors.toList());
    }

    @Override
    public void cleanCart(LocalDateTime dateTime) {
        LocalDateTime yesterdayDate = dateTime.minusDays(1);
        List<CartItem> cartItems = cartRepository.findAllByDateAddedBefore(yesterdayDate);
        cartItems.forEach(c -> cartRepository.deleteById(c.getId()));
    }

    @Override
    public CartItem getCartItemById(String productId) {
        return cartRepository.findById(productId).orElse(null);
    }

    @Override
    public void incQuantity(String productId, Integer incWith) {
        if (incWith > 0) {
            CartItem cartItem = getCartItemById(productId);
            cartItem.setQuantity(cartItem.getQuantity() + incWith);
            cartRepository.save(cartItem);
        }
    }

    @Override
    public void decQuantity(String productId, Integer decWith) {
        if (decWith > 0) {
            CartItem cartItem = getCartItemById(productId);
            cartItem.setQuantity(cartItem.getQuantity() - decWith);
            if (cartItem.getQuantity() <= 0) {
                cartRepository.delete(cartItem);
            } else {
                cartRepository.save(cartItem);
            }
        }
    }
}
