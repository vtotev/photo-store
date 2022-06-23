package softuni.photostore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.photostore.model.binding.BagAddBindingModel;
import softuni.photostore.model.binding.BagBrandAddBindingModel;
import softuni.photostore.model.binding.BagEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.bags.BagBrand;
import softuni.photostore.model.entity.bags.BagModel;
import softuni.photostore.model.entity.enums.BagTypeEnum;
import softuni.photostore.model.service.BagFilterModel;
import softuni.photostore.model.view.BagManageViewModel;
import softuni.photostore.model.view.BagViewModel;
import softuni.photostore.model.view.HomepageItemViewModel;
import softuni.photostore.repository.BagBrandRepository;
import softuni.photostore.repository.BagRepository;
import softuni.photostore.service.BagsService;
import softuni.photostore.service.PictureService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Service
public class BagsServiceImpl implements BagsService {
    private final BagBrandRepository brandRepository;
    private final BagRepository bagsRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public BagsServiceImpl(BagBrandRepository brandRepository, BagRepository bagsRepository, PictureService pictureService, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.bagsRepository = bagsRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    // BRAND METHODS

    @Override
    public List<BagBrand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public boolean isBrandExisting(String brandName) {
        return brandRepository.findByBrandName(brandName).isPresent();
    }

    @Override
    public boolean registerNewBrand(BagBrandAddBindingModel brand) {
        if (isBrandExisting(brand.getBrandName())) {
            return false;
        }
        BagBrand newBrand = new BagBrand(brand.getBrandName());
        brandRepository.save(newBrand);
        return true;
    }

    @Override
    public void deleteBrandWithId(String id) {
        brandRepository.deleteById(id);
    }

    @Override
    public BagBrand getBrandByName(String brand) {
        return brandRepository.findByBrandName(brand).orElse(null);
    }

    @Override
    public List<BagManageViewModel> getAllBagsForManagement() {
        return bagsRepository.findAll()
                .stream().map(bagModel -> modelMapper.map(bagModel, BagManageViewModel.class))
                .collect(Collectors.toList());
    }

    // BAG MODELS

    @Override
    public boolean addNewBag(BagAddBindingModel bagAdd) {
        PictureEntity picture = null;
        picture = pictureService.addPicture(bagAdd.getModelName(), bagAdd.getPictures());
        BagModel bagModel = modelMapper.map(bagAdd, BagModel.class);
        bagModel
                .setBrand(this.getBrandByName(bagAdd.getBrand()))
                .setPictures(picture);
        bagsRepository.save(bagModel);
        return true;
    }

    @Override
    @Transactional
    public void deleteModelById(String id) {
        BagModel toDelete = bagsRepository.findById(id).orElse(null);
        pictureService.deletePicture(toDelete.getPictures());
        bagsRepository.delete(toDelete);
    }

    @Override
    public List<BagViewModel> getAllBagsByTypeForOverview(String type) {
        return bagsRepository
                .findAllByBagType(BagTypeEnum.valueOf(type.toUpperCase()))
                .stream().map(bagModel -> modelMapper.map(bagModel, BagViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BagViewModel getBagDetailsById(String id) {
        return bagsRepository.findById(id)
                .map(bagModel -> modelMapper.map(bagModel, BagViewModel.class))
                .orElse(null);
    }

    @Override
    public List<BagViewModel> getAllBagsByFilterCriteria(BagFilterModel filter, String bagType) {
        return bagsRepository.findAllByFilterCriteria(
                        bagType,
                        (filter.getBrand() != null && filter.getBrand() != "") ? filter.getBrand() : null,
                        filter.getPriceFrom() != null ? filter.getPriceFrom() : null,
                        filter.getPriceTo() != null ? filter.getPriceTo() : null)
                .stream().map(bagModel -> modelMapper.map(bagModel, BagViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BagModel getBagById(String id) {
        return bagsRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editBag(String id, BagEditBindingModel editModel) {
        BagModel bagToEdit = this.getBagById(id);
        PictureEntity oldPicture = bagToEdit.getPictures();
        PictureEntity picture = pictureService.updatePicture(bagToEdit.getPictures(), editModel.getPictures());
        bagToEdit = modelMapper.map(editModel, BagModel.class);
        bagToEdit
                .setBrand(this.getBrandByName(editModel.getBrand()))
                .setPictures(picture);
        bagsRepository.save(bagToEdit);
        if (picture.getId() != oldPicture.getId()) {
            pictureService.deletePicture(oldPicture);
        }
        return true;
    }

    @Override
    public List<HomepageItemViewModel> getRandom3Bags() {
        List<HomepageItemViewModel> models = new ArrayList<>();
        bagsRepository
                .findRandom3Bags()
                .stream().map(c -> {
                    HomepageItemViewModel model = modelMapper.map(c, HomepageItemViewModel.class);
                    model.setItemUrl("bags/details/"+c.getId());
                    return model;
                }).forEach(models::add);
        return models;
    }


}
