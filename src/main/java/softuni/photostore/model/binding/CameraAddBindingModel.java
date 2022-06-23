package softuni.photostore.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class CameraAddBindingModel extends BaseEntity {

    @NotBlank
    private String brand;

    @NotBlank
    @Size(min = 3, max = 250)
    private String modelName;

    @NotNull
    private CameraTypeEnum cameraType;

    @NotNull
    private CameraSensorSizeEnum sensorSize;

    @NotNull
    @Min(value = 0)
    private Double megapixels;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @NotBlank
    @Size(min = 10)
    private String description;

    private MultipartFile picture;

    public String getBrand() {
        return brand;
    }

    public CameraAddBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public CameraAddBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public CameraTypeEnum getCameraType() {
        return cameraType;
    }

    public CameraAddBindingModel setCameraType(CameraTypeEnum cameraType) {
        this.cameraType = cameraType;
        return this;
    }

    public CameraSensorSizeEnum getSensorSize() {
        return sensorSize;
    }

    public CameraAddBindingModel setSensorSize(CameraSensorSizeEnum sensorSize) {
        this.sensorSize = sensorSize;
        return this;
    }

    public Double getMegapixels() {
        return megapixels;
    }

    public CameraAddBindingModel setMegapixels(Double megapixels) {
        this.megapixels = megapixels;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CameraAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CameraAddBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CameraAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public CameraAddBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
