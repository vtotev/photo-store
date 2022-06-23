package softuni.photostore.model.view;

import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.bags.BagBrand;

import java.math.BigDecimal;

public class BagViewModel {

    private String id;
    private BagBrand brand;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private PictureEntity pictures;

    public BagViewModel() {
    }

    public String getId() {
        return id;
    }

    public BagViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public BagBrand getBrand() {
        return brand;
    }

    public BagViewModel setBrand(BagBrand brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public BagViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BagViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BagViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BagViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPictures() {
        return pictures;
    }

    public BagViewModel setPictures(PictureEntity pictures) {
        this.pictures = pictures;
        return this;
    }
}
