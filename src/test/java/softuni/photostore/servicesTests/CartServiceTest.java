package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import softuni.photostore.model.entity.CartItem;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.view.CartItemListView;
import softuni.photostore.repository.CartRepository;
import softuni.photostore.repository.UsersRepository;
import softuni.photostore.service.UsersService;
import softuni.photostore.service.impl.CartServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    private CartServiceImpl sut;
    @Mock
    private CartRepository mockedCart;
    @Mock
    private ModelMapper mockedMapper;
    @Mock
    private UsersService mockedUsersService;
    @Mock
    private UsersRepository mockedUsersRepo;

    @BeforeEach
    public void setup() {
        sut = new CartServiceImpl(mockedCart, mockedMapper, mockedUsersService);
    }

//    @Test
//    public void testAddNewItem() {
//        User user = new User();
//        user.setId("id");
//        CameraModel product = new CameraModel();
//        product
//                .setSensorSize(CameraSensorSizeEnum.FULL_FRAME)
//                .setMegapixels(24d)
//                .setCameraType(CameraTypeEnum.DSLR)
//                .setBrand(new CameraBrand())
//                .setQuantity(1)
//                .setPrice(BigDecimal.TEN)
//                .setId("id")
//                .setDescription("desc")
//                .setModelName("model")
//                .setPictures(new PictureEntity());
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication())
//                .thenReturn(Mockito.mock(Authentication.class));
//        Mockito.when(securityContext.getAuthentication().getName())
//                .thenReturn("id");
//        Mockito.when(mockedUsersRepo.findByUsernameIgnoreCase("id"))
//                .thenReturn(Optional.of(new User()));
////        Mockito.when(new AuthenticationTrustResolverImpl().isAnonymous(securityContext.getAuthentication()))
////                .thenReturn(true);
//        Mockito.when(mockedCart.save(Mockito.any()))
//                .thenReturn(CartItem.class);
//        CartItem cartItem = sut.addItemToCart(product, securityContext, CameraModel.class);
//        assertThat(cartItem).isNotNull();
//        assertThat(cartItem.getProductName()).isEqualTo(product.getModelName());
//    }

    @Test
    public void testGetCartForCurrentUser() {
        User user = new User();
        user.setId("id");
        CartItem cart = new CartItem();
        cart
                .setQuantity(1)
                .setDateAdded(LocalDateTime.MIN)
                .setPrice(BigDecimal.TEN)
                .setCustomerId(user.getId())
                .setCustomerIP(null)
                .setPictureUrl("url")
                .setProductId("productId")
                .setProductName("name")
                .setProductType("type")
                .setId("id");

        assertThat(cart.getQuantity()).isEqualTo(1);
        assertThat(cart.getDateAdded()).isEqualTo(LocalDateTime.MIN);
        assertThat(cart.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(cart.getCustomerId()).isEqualTo(user.getId());
        assertThat(cart.getCustomerIP()).isNull();
        assertThat(cart.getPictureUrl()).isEqualTo("url");
        assertThat(cart.getProductId()).isEqualTo("productId");
        assertThat(cart.getProductName()).isEqualTo("name");
        assertThat(cart.getProductType()).isEqualTo("type");
        assertThat(cart.getId()).isEqualTo("id");

        Mockito.when(mockedCart.getAllByCustomerId("id"))
                .thenReturn(List.of(cart));

        List<CartItemListView> cartItems = sut.getCartForCurrentUserOrIP(user, null);
        assertThat(cartItems.size()).isEqualTo(1);
    }

    @Test
    public void testGetCartForCurrentIP() {
        User user = new User();
        user.setId("id");
        CartItem cart = new CartItem();

        Mockito.when(mockedCart.getAllByCustomerIP("IP"))
                .thenReturn(List.of(cart));

        List<CartItemListView> cartItems = sut.getCartForCurrentUserOrIP(null, "IP");
        assertThat(cartItems.size()).isEqualTo(1);
    }

    @Test
    public void testRemoveItemFromCartById() {
        sut.removeItemFromCartById("id");
        Mockito.verify(mockedCart, Mockito.times(1)).deleteById("id");
    }

}
