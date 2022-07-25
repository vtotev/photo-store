package softuni.photostore.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import softuni.photostore.model.binding.TripodAddBindingModel;
import softuni.photostore.model.binding.TripodBrandAddBindingModel;
import softuni.photostore.model.binding.TripodEditBindingModel;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.tripods.TripodBrand;
import softuni.photostore.model.entity.tripods.TripodModel;
import softuni.photostore.model.service.TripodFilterModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.model.view.TripodManageViewModel;
import softuni.photostore.model.view.TripodViewModel;

import java.util.List;

public interface TripodService {


    // BRAND METHODS

    List<TripodBrand> getAllBrands();

    boolean isBrandExisting(String brandName);

    boolean registerNewBrand(TripodBrandAddBindingModel brand);

    void deleteBrandWithId(String id);

    TripodBrand getBrandByName(String brand);

    // MODEL METHODS

    List<TripodViewModel> getAllTripodsOverviewModel();

    List<TripodManageViewModel> getAllTripodsForManagement();

    boolean addNewTripod(TripodAddBindingModel tripodAdd);

    void deleteModelById(String id);

    TripodModel getTripodById(String id);

    boolean editTripod(String id, TripodEditBindingModel editModel);

    TripodViewModel getTripodDetails(String id);

    List<TripodViewModel> getAllTripodsByFilterCriteria(TripodFilterModel filter);

    List<HomepageItemViewModel> getRandom3Tripods();

    @CacheEvict(value = "randomTripods", allEntries = true)
    default void clearCache() {

    }
}
