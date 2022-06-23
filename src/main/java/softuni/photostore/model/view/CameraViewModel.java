package softuni.photostore.model.view;

import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;

public class CameraViewModel extends BaseEntity {
    private String id;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private PictureEntity pictures;

    public CameraViewModel() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public CameraViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public CameraViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CameraViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CameraViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CameraViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPictures() {
        return pictures;
    }

    public CameraViewModel setPictures(PictureEntity pictures) {
        this.pictures = pictures;
        return this;
    }
}
