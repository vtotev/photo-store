package softuni.photostore.service;

import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.view.HomepageItemViewModel;

import java.util.List;

public interface HomeService {
    void cleanRandom3ProductsCache();
    List<HomepageItemViewModel> getRandom3ProductsForHomepage();
}
