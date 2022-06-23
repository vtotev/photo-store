package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.photostore.model.binding.FlashAddBindingModel;
import softuni.photostore.model.binding.FlashBrandAddBindingModel;
import softuni.photostore.model.binding.FlashEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.enums.FlashModesEnum;
import softuni.photostore.model.entity.flashes.FlashBrand;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.service.FlashFilterModel;
import softuni.photostore.model.view.FlashManageViewModel;
import softuni.photostore.model.view.FlashViewModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.repository.FlashBrandRepository;
import softuni.photostore.repository.FlashRepository;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.FlashService;
import softuni.photostore.service.PictureService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Service
public class FlashServiceImpl implements FlashService {
    private final FlashBrandRepository brandRepository;
    private final FlashRepository flashRepository;
    private final CameraService cameraService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public FlashServiceImpl(FlashBrandRepository brandRepository, FlashRepository flashRepository, CameraService cameraService, PictureService pictureService, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.flashRepository = flashRepository;
        this.cameraService = cameraService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    // BRANDS METHODS
    @Override
    public List<FlashBrand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public boolean isBrandExisting(String brandName) {
        return brandRepository.findByBrandName(brandName).isPresent();
    }

    @Override
    public boolean registerNewBrand(FlashBrandAddBindingModel brand) {
        if (isBrandExisting(brand.getBrandName())) {
            return false;
        }
        FlashBrand newBrand = new FlashBrand(brand.getBrandName());
        brandRepository.save(newBrand);
        return true;
    }

    @Override
    public void deleteBrandWithId(String id) {
        brandRepository.deleteById(id);
    }

    @Override
    public FlashBrand getBrandByName(String brand) {
        return brandRepository.findByBrandName(brand).orElse(null);
    }


    // FLASH MODEL METHODS

    @Override
    public List<FlashViewModel> getAllFlashesByBrandCompatibilityForOverview(String brand) {
        return flashRepository
                .findAllByBrandCompatibility(cameraService.getBrandByName(brand))
                .stream().map(flashModel -> modelMapper.map(flashModel, FlashViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlashManageViewModel> getAllFlashesForManagement() {
        return flashRepository
                .findAll()
                .stream()
                .map(flashModel -> modelMapper.map(flashModel, FlashManageViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addNewFlash(FlashAddBindingModel flashAdd) {
        PictureEntity picture = null;
        picture = pictureService.addPicture(flashAdd.getModelName(), flashAdd.getPictures());
        FlashModel flashModel = modelMapper.map(flashAdd, FlashModel.class);
        flashModel
                .setBrand(this.getBrandByName(flashAdd.getBrand()))
                .setBrandCompatibility(cameraService.getBrandByName(flashAdd.getBrandCompatibility()))
                .setPictures(picture);
        flashRepository.save(flashModel);
        return true;
    }

    @Override
    @Transactional
    public void deleteModelById(String id) {
        FlashModel toDelete = flashRepository.findById(id).orElse(null);
        pictureService.deletePicture(toDelete.getPictures());
        flashRepository.delete(toDelete);
    }

    @Override
    public FlashModel getFlashById(String id) {
        return flashRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editFlash(String id, FlashEditBindingModel editModel) {
        FlashModel flashToEdit = this.getFlashById(id);
        PictureEntity oldPicture = flashToEdit.getPictures();
        PictureEntity picture = pictureService.updatePicture(flashToEdit.getPictures(), editModel.getPictures());
        flashToEdit = modelMapper.map(editModel, FlashModel.class);
        flashToEdit.setBrand(this.getBrandByName(editModel.getBrand()))
                .setBrandCompatibility(cameraService.getBrandByName(editModel.getBrandCompatibility()))
                .setPictures(picture);
        flashRepository.save(flashToEdit);
        if (picture.getId() != oldPicture.getId()) {
            pictureService.deletePicture(oldPicture);
        }
        return true;
    }

    @Override
    public FlashViewModel getFlashDetailsViewById(String id) {
        return flashRepository.findById(id)
                .map(flashModel -> modelMapper.map(flashModel, FlashViewModel.class)).orElse(null);
    }

    @Override
    public List<HomepageItemViewModel> getRandom3Flashes() {
        List<HomepageItemViewModel> models = new ArrayList<>();
        flashRepository
                .findRandom3Flashes()
                .stream().map(c -> {
                    HomepageItemViewModel model = modelMapper.map(c, HomepageItemViewModel.class);
                    model.setItemUrl("flashes/details/"+c.getId());
                    return model;
                }).forEach(models::add);
        return models;
    }

    @Override
    public List<FlashViewModel> getAllFlashesByFilterCriteria(FlashFilterModel filter, String flashBrandCompatibility) {
        return flashRepository.findAllByFilterCriteria(
                        cameraService.getBrandByName(flashBrandCompatibility).getId(),
                        (filter.getBrand() != null && filter.getBrand() != "") ? filter.getBrand() : null,
                        (filter.getFlashType() != "" && filter.getFlashType() != null) ? FlashModesEnum.valueOf(filter.getFlashType().toUpperCase()).name() : null,
                        filter.getHSS() ? true : null,
                        filter.getPriceFrom() != null ? filter.getPriceFrom() : null,
                        filter.getPriceTo() != null ? filter.getPriceTo() : null)
                .stream().map(flashModel -> modelMapper.map(flashModel, FlashViewModel.class))
                .collect(Collectors.toList());
    }
}
