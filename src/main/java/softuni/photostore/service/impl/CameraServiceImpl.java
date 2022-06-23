package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.photostore.model.binding.CameraAddBindingModel;
import softuni.photostore.model.binding.CameraBrandAddBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.service.CameraFilterModel;
import softuni.photostore.model.view.CameraManageViewModel;
import softuni.photostore.model.view.CameraViewModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.repository.CameraBrandRepository;
import softuni.photostore.repository.CameraRepository;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.PictureService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService {

    private final CameraRepository cameraRepository;
    private final CameraBrandRepository brandRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public CameraServiceImpl(CameraRepository cameraRepository, CameraBrandRepository brandRepository, PictureService pictureService, ModelMapper modelMapper) {
        this.cameraRepository = cameraRepository;
        this.brandRepository = brandRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CameraModel> getAllCamerasByType(String type) {
        return cameraRepository.findAllByCameraType(CameraTypeEnum.valueOf(type.toUpperCase()));
    }

    @Override
    public boolean addNewCamera(CameraAddBindingModel cameraAddBindingModel) {
        PictureEntity picture = null;
        picture = pictureService.addPicture(cameraAddBindingModel.getModelName(), cameraAddBindingModel.getPicture());
        CameraModel camera = modelMapper.map(cameraAddBindingModel, CameraModel.class);
        camera.setBrand(this.getBrandByName(cameraAddBindingModel.getBrand()))
                .setPictures(picture);

        cameraRepository.save(camera);
        return true;
    }

    @Override
    public List<CameraViewModel> getAllCamerasByFilterCriteria(CameraFilterModel filter, String camType) {
        return cameraRepository.findAllByFilterCriteria(
                camType,
                (filter.getBrand() != null && filter.getBrand() != "") ? filter.getBrand() : null,
                (filter.getSensorSize() != "" && filter.getSensorSize() != null) ? CameraSensorSizeEnum.valueOf(filter.getSensorSize().toUpperCase()).name() : null,
                filter.getPriceFrom() != null ? filter.getPriceFrom() : null,
                filter.getPriceTo() != null ? filter.getPriceTo() : null)
                .stream()
                .map(c -> modelMapper.map(c, CameraViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CameraModel getCameraById(String id) {
        return cameraRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editCamera(String id, CameraAddBindingModel editModel) {
        CameraModel camToEdit = this.getCameraById(id);
        PictureEntity oldPicture = camToEdit.getPictures();
        PictureEntity picture = pictureService.updatePicture(camToEdit.getPictures(), editModel.getPicture());
        camToEdit = modelMapper.map(editModel, CameraModel.class);
        camToEdit.setBrand(this.getBrandByName(editModel.getBrand()))
                .setPictures(picture);
        cameraRepository.save(camToEdit);
        if (picture.getId() != oldPicture.getId()) {
            pictureService.deletePicture(oldPicture);
        }
        return true;
    }

    @Override
    @Transactional
    public void deleteModelById(String id) {
        CameraModel toDelete = cameraRepository.findById(id).orElse(null);
        pictureService.deletePicture(toDelete.getPictures());
        cameraRepository.delete(toDelete);
    }


    // FOR BRANDS

    @Override
    public List<CameraBrand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteBrandWithId(String id) {
        brandRepository.deleteById(id);
    }

    @Override
    public CameraBrand getBrandByName(String name) {
        return brandRepository.findByBrandName(name).orElse(null)   ;
    }

    @Override
    public CameraViewModel getCameraDetailsById(String id) {
        return cameraRepository.findById(id)
                .map(cameraModel -> modelMapper.map(cameraModel, CameraViewModel.class))
                .orElse(null);
    }

    @Override
    public List<CameraManageViewModel> getAllCamerasForManagement() {
        return cameraRepository.findAll()
                .stream().map(cameraModel -> modelMapper.map(cameraModel, CameraManageViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<HomepageItemViewModel> getRandom3Cameras() {
        List<HomepageItemViewModel> models = new ArrayList<>();
        cameraRepository
                .findRandom3Cameras()
                .stream().map(c -> {
                    HomepageItemViewModel model = modelMapper.map(c, HomepageItemViewModel.class);
                    model.setItemUrl("cameras/details/"+c.getId());
                    return model;
                }).forEach(models::add);
        return models;
    }

    @Override
    public boolean registerNewBrand(CameraBrandAddBindingModel brand) {
        if (isBrandExisting(brand.getBrandName())) {
            return false;
        }
        CameraBrand newBrand = new CameraBrand(brand.getBrandName());
        brandRepository.save(newBrand);
        return true;
    }

    @Override
    public boolean isBrandExisting(String brand) {
        return brandRepository.findByBrandName(brand).isPresent();
    }

}