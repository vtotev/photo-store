package softuni.photostore.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import softuni.photostore.model.binding.FlashAddBindingModel;
import softuni.photostore.model.binding.FlashBrandAddBindingModel;
import softuni.photostore.model.binding.FlashEditBindingModel;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.flashes.FlashBrand;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.service.FlashFilterModel;
import softuni.photostore.model.view.FlashManageViewModel;
import softuni.photostore.model.view.FlashViewModel;
import softuni.photostore.model.view.HomepageItemViewModel;

import java.util.List;

public interface FlashService {

    // BRAND METHODS

    List<FlashBrand> getAllBrands();

    boolean isBrandExisting(String brandName);

    boolean registerNewBrand(FlashBrandAddBindingModel brand);

    void deleteBrandWithId(String id);

    FlashBrand getBrandByName(String brand);


    // MODEL METHODS

    List<FlashViewModel> getAllFlashesByBrandCompatibilityForOverview(String brand);

    List<FlashManageViewModel> getAllFlashesForManagement();

    boolean addNewFlash(FlashAddBindingModel flashAdd);

    List<FlashViewModel> getAllFlashesByFilterCriteria(FlashFilterModel filter, String flashBrandCompatibility);

    void deleteModelById(String id);

    FlashModel getFlashById(String id);

    boolean editFlash(String id, FlashEditBindingModel editModel);

    FlashViewModel getFlashDetailsViewById(String id);

    List<HomepageItemViewModel> getRandom3Flashes();

    @CacheEvict(value = "randomFlashes", allEntries = true)
    default void clearCache() {

    }
}
