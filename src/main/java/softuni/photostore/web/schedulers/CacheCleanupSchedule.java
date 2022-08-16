package softuni.photostore.web.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.photostore.service.*;

@Component
public class CacheCleanupSchedule {

    private HomeService homeService;

    public CacheCleanupSchedule(HomeService homeService) {
        this.homeService = homeService;
    }

    @Scheduled(fixedRate = 3600000)
    public void cacheCleanup() {
        homeService.cleanRandom3ProductsCache();
    }

}
