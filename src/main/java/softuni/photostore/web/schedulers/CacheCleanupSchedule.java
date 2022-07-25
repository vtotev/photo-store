package softuni.photostore.web.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.photostore.service.*;

import java.time.LocalDateTime;

@Component
public class CacheCleanupSchedule {

    private BagsService bagsService;
    private CameraService cameraService;
    private FlashService flashService;
    private LensService lensService;
    private TripodService tripodService;

    public CacheCleanupSchedule(BagsService bagsService, CameraService cameraService, FlashService flashService, LensService lensService, TripodService tripodService) {
        this.bagsService = bagsService;
        this.cameraService = cameraService;
        this.flashService = flashService;
        this.lensService = lensService;
        this.tripodService = tripodService;
    }

    @Scheduled(fixedRate = 3600000)
    public void dailyCleanCart() {
        bagsService.clearCache();
        cameraService.clearCache();
        flashService.clearCache();
        lensService.clearCache();
        tripodService.clearCache();
    }

}
