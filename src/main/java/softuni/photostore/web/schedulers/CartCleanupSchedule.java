package softuni.photostore.web.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.photostore.service.CartService;

import java.time.LocalDateTime;

@Component
public class CartCleanupSchedule {

    private CartService cartService;

    public CartCleanupSchedule(CartService cartService) {
        this.cartService = cartService;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void dailyCleanCart() {
        cartService.cleanCart(LocalDateTime.now());
    }

}
