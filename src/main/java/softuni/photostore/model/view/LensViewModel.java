package softuni.photostore.model.view;

import softuni.photostore.model.entity.PictureEntity;

import java.math.BigDecimal;

public class LensViewModel {
    private String id;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private PictureEntity pictures;

    public String getId() {
        return id;
    }

    public LensViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public LensViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LensViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LensViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LensViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPictures() {
        return pictures;
    }

    public LensViewModel setPictures(PictureEntity pictures) {
        this.pictures = pictures;
        return this;
    }
}
