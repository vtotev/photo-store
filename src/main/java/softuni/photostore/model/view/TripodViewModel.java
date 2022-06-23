package softuni.photostore.model.view;

import softuni.photostore.model.entity.PictureEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TripodViewModel {

    private String id;
    private String brand;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private PictureEntity pictures;

    public TripodViewModel() {
    }

    public String getId() {
        return id;
    }

    public TripodViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public TripodViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public TripodViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TripodViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TripodViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TripodViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPictures() {
        return pictures;
    }

    public TripodViewModel setPictures(PictureEntity pictures) {
        this.pictures = pictures;
        return this;
    }
}
