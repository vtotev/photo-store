package softuni.photostore.service.impl;

import org.springframework.stereotype.Service;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class HomeServiceImpl implements HomeService {

    private final CameraService cameraService;
    private final LensService lensService;
    private final FlashService flashService;
    private final BagsService bagsService;
    private final TripodService tripodService;

    public HomeServiceImpl(CameraService cameraService, LensService lensService, FlashService flashService, BagsService bagsService, TripodService tripodService) {
        this.cameraService = cameraService;
        this.lensService = lensService;
        this.flashService = flashService;
        this.bagsService = bagsService;
        this.tripodService = tripodService;
    }


    @Override
    public List<HomepageItemViewModel> getRandom3ProductsForHomepage() {
        List<HomepageItemViewModel> models = new ArrayList<>();
        models.addAll(cameraService.getRandom3Cameras());
        models.addAll(lensService.getRandom3Lenses());
        models.addAll(flashService.getRandom3Flashes());
        models.addAll(bagsService.getRandom3Bags());
        models.addAll(tripodService.getRandom3Tripods());
        models.sort((o1, o2) -> Integer.compare(o1.hashCode(), o2.hashCode()));
        return models;
    }
}