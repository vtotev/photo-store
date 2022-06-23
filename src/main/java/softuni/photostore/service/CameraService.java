package softuni.photostore.service;

import softuni.photostore.model.binding.CameraAddBindingModel;
import softuni.photostore.model.binding.CameraBrandAddBindingModel;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.service.CameraFilterModel;
import softuni.photostore.model.view.CameraManageViewModel;
import softuni.photostore.model.view.CameraViewModel;
import softuni.photostore.model.view.HomepageItemViewModel;

import java.util.List;

public interface CameraService {
    List<CameraModel> getAllCamerasByType(String type);

    boolean addNewCamera(CameraAddBindingModel cameraAddBindingModel);

    List<CameraViewModel> getAllCamerasByFilterCriteria(CameraFilterModel filter, String camType);

    CameraModel getCameraById(String id);

    boolean editCamera(String id, CameraAddBindingModel cameraAddBindingModel);

    void deleteModelById(String id);


    // FOR BRANDS

    boolean registerNewBrand(CameraBrandAddBindingModel brand);
    List<CameraBrand> getAllBrands();
    boolean isBrandExisting(String brand);
    void deleteBrandWithId(String id);
    CameraBrand getBrandByName(String name);

    CameraViewModel getCameraDetailsById(String id);

    List<CameraManageViewModel> getAllCamerasForManagement();

    List<HomepageItemViewModel> getRandom3Cameras();
}
