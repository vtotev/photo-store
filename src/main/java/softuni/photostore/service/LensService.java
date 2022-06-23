package softuni.photostore.service;

import softuni.photostore.model.binding.LensAddBindingModel;
import softuni.photostore.model.binding.LensBrandAddBindingModel;
import softuni.photostore.model.binding.LensEditBindingModel;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.lenses.LensBrand;
import softuni.photostore.model.entity.lenses.LensModel;
import softuni.photostore.model.service.LensFilterModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.model.view.LensManageViewModel;
import softuni.photostore.model.view.LensViewModel;

import java.util.List;

public interface LensService {

    // BRAND METHODS

    List<LensBrand> getAllBrands();

    boolean isBrandExisting(String brandName);

    boolean registerNewBrand(LensBrandAddBindingModel brand);

    void deleteBrandWithId(String id);

    LensBrand getBrandByName(String brand);


    // MODEL METHODS

    List<LensManageViewModel> getAllLensesForManagement();

    boolean addNewLens(LensAddBindingModel lensAdd);

    List<LensViewModel> getAllLensesByTypeForOverview(String type);

    List<LensViewModel> getAllLensesByFilterCriteria(LensFilterModel filter, String lensType);

    void deleteModelById(String id);

    LensModel getLensById(String id);

    boolean editLens(String id, LensEditBindingModel editModel);

    LensViewModel getLensDetailsById(String id);

    List<HomepageItemViewModel> getRandom3Lenses();
}
