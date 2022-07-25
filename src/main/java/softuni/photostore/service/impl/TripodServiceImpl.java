package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import softuni.photostore.model.binding.TripodAddBindingModel;
import softuni.photostore.model.binding.TripodBrandAddBindingModel;
import softuni.photostore.model.binding.TripodEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.tripods.TripodBrand;
import softuni.photostore.model.entity.tripods.TripodModel;
import softuni.photostore.model.service.TripodFilterModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.model.view.TripodManageViewModel;
import softuni.photostore.model.view.TripodViewModel;
import softuni.photostore.repository.TripodBrandRepository;
import softuni.photostore.repository.TripodRepository;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.TripodService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripodServiceImpl implements TripodService {
    private final TripodRepository tripodRepository;
    private final TripodBrandRepository brandRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public TripodServiceImpl(TripodRepository tripodRepository, TripodBrandRepository brandRepository, PictureService pictureService, ModelMapper modelMapper) {
        this.tripodRepository = tripodRepository;
        this.brandRepository = brandRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    // BRAND METHODS

    @Override
    public List<TripodBrand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public boolean isBrandExisting(String brandName) {
        return brandRepository.findByBrandName(brandName).isPresent();
    }

    @Override
    public boolean registerNewBrand(TripodBrandAddBindingModel brand) {
        if (isBrandExisting(brand.getBrandName())) {
            return false;
        }
        TripodBrand newBrand = new TripodBrand(brand.getBrandName());
        brandRepository.save(newBrand);
        return true;
    }

    @Override
    public void deleteBrandWithId(String id) {
        brandRepository.deleteById(id);
    }

    @Override
    public TripodBrand getBrandByName(String brand) {
        return brandRepository.findByBrandName(brand).orElse(null);
    }

    // MODELS METHODS

    @Override
    public List<TripodViewModel> getAllTripodsOverviewModel() {
        return tripodRepository.findAll()
                .stream()
                .map(tripodModel -> modelMapper.map(tripodModel, TripodViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripodManageViewModel> getAllTripodsForManagement() {
        return tripodRepository.findAll()
                .stream().map(tripodModel -> modelMapper.map(tripodModel, TripodManageViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addNewTripod(TripodAddBindingModel tripodAdd) {
        PictureEntity picture = null;
        picture = pictureService.addPicture(tripodAdd.getModelName(), tripodAdd.getPictures());
        TripodModel tripodModel = modelMapper.map(tripodAdd, TripodModel.class);
        tripodModel
                .setBrand(this.getBrandByName(tripodAdd.getBrand()))
                .setPictures(picture);
        tripodRepository.save(tripodModel);
        return true;
    }

    @Override
    @Transactional
    public void deleteModelById(String id) {
        TripodModel toDelete = tripodRepository.findById(id).orElse(null);
        pictureService.deletePicture(toDelete.getPictures());
        tripodRepository.delete(toDelete);
    }

    @Override
    public TripodModel getTripodById(String id) {
        return tripodRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editTripod(String id, TripodEditBindingModel editModel) {
        TripodModel tripodToEdit = this.getTripodById(id);
        PictureEntity oldPicture = tripodToEdit.getPictures();
        PictureEntity picture = pictureService.updatePicture(tripodToEdit.getPictures(), editModel.getPictures());
        tripodToEdit = modelMapper.map(editModel, TripodModel.class);
        tripodToEdit
                .setBrand(this.getBrandByName(editModel.getBrand()))
                .setPictures(picture);
        tripodRepository.save(tripodToEdit);
        if (picture.getId() != oldPicture.getId()) {
            pictureService.deletePicture(oldPicture);
        }
        return true;
    }

    @Override
    public TripodViewModel getTripodDetails(String id) {
        return modelMapper.map(this.getTripodById(id), TripodViewModel.class);
    }

    @Override
    public List<TripodViewModel> getAllTripodsByFilterCriteria(TripodFilterModel filter) {
        return tripodRepository.findAllByFilterCriteria(
                        (filter.getBrand() != null && filter.getBrand() != "") ? filter.getBrand() : null,
                        filter.getPriceFrom() != null ? filter.getPriceFrom() : null,
                        filter.getPriceTo() != null ? filter.getPriceTo() : null)
                .stream().map(tripodModel -> modelMapper.map(tripodModel, TripodViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("randomTripods")
    public List<HomepageItemViewModel> getRandom3Tripods() {
        List<HomepageItemViewModel> models = new ArrayList<>();
        tripodRepository
                .findRandom3Tripods()
                .stream().map(c -> {
                    HomepageItemViewModel model = modelMapper.map(c, HomepageItemViewModel.class);
                    model.setItemUrl("tripods/details/"+c.getId());
                    return model;
                }).forEach(models::add);
        return models;
    }


}
