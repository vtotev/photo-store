package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.photostore.model.binding.LensAddBindingModel;
import softuni.photostore.model.binding.LensBrandAddBindingModel;
import softuni.photostore.model.binding.LensEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.LensMountTypeEnum;
import softuni.photostore.model.entity.lenses.LensBrand;
import softuni.photostore.model.entity.lenses.LensModel;
import softuni.photostore.model.service.LensFilterModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.model.view.LensManageViewModel;
import softuni.photostore.model.view.LensViewModel;
import softuni.photostore.repository.LensBrandRepository;
import softuni.photostore.repository.LensRepository;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.LensService;
import softuni.photostore.service.PictureService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Service
public class LensServiceImpl implements LensService {

    private final LensRepository lensRepository;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final LensBrandRepository brandRepository;
    private final CameraService cameraService;

    public LensServiceImpl(LensRepository lensRepository, ModelMapper modelMapper, PictureService pictureService, LensBrandRepository brandRepository, CameraService cameraService) {
        this.lensRepository = lensRepository;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.brandRepository = brandRepository;
        this.cameraService = cameraService;
    }

    // LENS MODEL METHODS

    @Override
    public List<LensManageViewModel> getAllLensesForManagement() {
        return lensRepository
                .findAll()
                .stream()
                .map(lensModel -> modelMapper.map(lensModel, LensManageViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addNewLens(LensAddBindingModel lensAdd) {
        PictureEntity picture = null;
        picture = pictureService.addPicture(lensAdd.getModelName(), lensAdd.getPicture());
        LensModel lensModel = modelMapper.map(lensAdd, LensModel.class);
        lensModel
                .setBrand(this.getBrandByName(lensAdd.getBrand()))
                .setCameraBrandCompatibility(cameraService.getBrandByName(lensAdd.getCameraBrandCompatibility()))
                .setPictures(picture);
        lensRepository.save(lensModel);
        return true;
    }

    @Override
    public List<LensViewModel> getAllLensesByTypeForOverview(String type) {
        return lensRepository
                .findAllByCameraTypeCompatibility(LensMountTypeEnum.valueOf(type.toUpperCase()))
                .stream().map(lensModel -> modelMapper.map(lensModel, LensViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LensViewModel> getAllLensesByFilterCriteria(LensFilterModel filter, String lensType) {
        return lensRepository.findAllByFilterCriteria(
                        lensType,
                        (filter.getBrand() != null && filter.getBrand() != "") ? filter.getBrand() : null,
                        (filter.getCameraCompatibility() != "" && filter.getCameraCompatibility() != null) ? filter.getCameraCompatibility() : null,
                        (filter.getSensorSize() != "" && filter.getSensorSize() != null) ? CameraSensorSizeEnum.valueOf(filter.getSensorSize().toUpperCase()).name() : null,
                        filter.isMaxAperture28() ? true : null,
                        filter.getPriceFrom() != null ? filter.getPriceFrom() : null,
                        filter.getPriceTo() != null ? filter.getPriceTo() : null)
                .stream().map(cameraModel -> modelMapper.map(cameraModel, LensViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteModelById(String id) {
        LensModel toDelete = lensRepository.findById(id).orElse(null);
        pictureService.deletePicture(toDelete.getPictures());
        lensRepository.delete(toDelete);
    }

    @Override
    public LensModel getLensById(String id) {
        return lensRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editLens(String id, LensEditBindingModel editModel) {
        LensModel lensToEdit = this.getLensById(id);
        PictureEntity oldPicture = lensToEdit.getPictures();
        PictureEntity picture = pictureService.updatePicture(lensToEdit.getPictures(), editModel.getPicture());
        lensToEdit = modelMapper.map(editModel, LensModel.class);
        lensToEdit.setBrand(this.getBrandByName(editModel.getBrand()))
                .setCameraBrandCompatibility(cameraService.getBrandByName(editModel.getCameraBrandCompatibility()))
                .setPictures(picture);
        lensRepository.save(lensToEdit);
        if (picture.getId() != oldPicture.getId()) {
            pictureService.deletePicture(oldPicture);
        }
        return true;
    }

    @Override
    public LensViewModel getLensDetailsById(String id) {
        return lensRepository.findById(id)
                .map(lensModel -> modelMapper.map(lensModel, LensViewModel.class))
                .orElse(null);
    }

    @Override
    public List<HomepageItemViewModel> getRandom3Lenses() {
        List<HomepageItemViewModel> models = new ArrayList<>();
        lensRepository
                .findRandom3Lenses()
                .stream().map(c -> {
                    HomepageItemViewModel model = modelMapper.map(c, HomepageItemViewModel.class);
                    model.setItemUrl("lenses/details/"+c.getId());
                    return model;
                }).forEach(models::add);
        return models;
    }

    // BRAND METHODS

    @Override
    public List<LensBrand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public boolean isBrandExisting(String brandName) {
        return brandRepository.findByBrandName(brandName).isPresent();
    }

    @Override
    public boolean registerNewBrand(LensBrandAddBindingModel brand) {
        if (isBrandExisting(brand.getBrandName())) {
            return false;
        }
        LensBrand newBrand = new LensBrand(brand.getBrandName());
        brandRepository.save(newBrand);
        return true;
    }

    @Override
    public void deleteBrandWithId(String id) {
        brandRepository.deleteById(id);
    }

    @Override
    public LensBrand getBrandByName(String brand) {
        return brandRepository.findByBrandName(brand).orElse(null);
    }


}
