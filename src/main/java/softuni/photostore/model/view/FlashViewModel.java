package softuni.photostore.model.view;

import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.flashes.FlashBrand;
import softuni.photostore.model.entity.lenses.LensBrand;

import java.math.BigDecimal;

public class FlashViewModel {
    private String id;
    private FlashBrand brand;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private PictureEntity pictures;

    public FlashViewModel() {
    }

    public String getId() {
        return id;
    }

    public FlashViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public FlashViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public FlashViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public FlashViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FlashViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPictures() {
        return pictures;
    }

    public FlashViewModel setPictures(PictureEntity pictures) {
        this.pictures = pictures;
        return this;
    }

    public FlashBrand getBrand() {
        return brand;
    }

    public FlashViewModel setBrand(FlashBrand brand) {
        this.brand = brand;
        return this;
    }
}