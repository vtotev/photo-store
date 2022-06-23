package softuni.photostore.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.LensMountTypeEnum;
import softuni.photostore.model.entity.enums.LensTypeEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class LensEditBindingModel {

    private String id;

    @NotBlank
    private String brand;

    @NotBlank
    @Size(min = 3, max = 250)
    private String modelName;

    @NotBlank
    private String cameraBrandCompatibility;

    @NotNull
    private LensMountTypeEnum cameraTypeCompatibility;

    @NotNull
    private CameraSensorSizeEnum sensorSizeCompatibility;

    @NotNull
    private LensTypeEnum lensType;


    @NotNull
    @Min(value = 0)
    private Double fastestAperture;

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

    public String getId() {
        return id;
    }

    public LensEditBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public LensEditBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public LensEditBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getCameraBrandCompatibility() {
        return cameraBrandCompatibility;
    }

    public LensEditBindingModel setCameraBrandCompatibility(String cameraBrandCompatibility) {
        this.cameraBrandCompatibility = cameraBrandCompatibility;
        return this;
    }

    public LensMountTypeEnum getCameraTypeCompatibility() {
        return cameraTypeCompatibility;
    }

    public LensEditBindingModel setCameraTypeCompatibility(LensMountTypeEnum cameraTypeCompatibility) {
        this.cameraTypeCompatibility = cameraTypeCompatibility;
        return this;
    }

    public CameraSensorSizeEnum getSensorSizeCompatibility() {
        return sensorSizeCompatibility;
    }

    public LensEditBindingModel setSensorSizeCompatibility(CameraSensorSizeEnum sensorSizeCompatibility) {
        this.sensorSizeCompatibility = sensorSizeCompatibility;
        return this;
    }

    public LensTypeEnum getLensType() {
        return lensType;
    }

    public LensEditBindingModel setLensType(LensTypeEnum lensType) {
        this.lensType = lensType;
        return this;
    }

    public Double getFastestAperture() {
        return fastestAperture;
    }

    public LensEditBindingModel setFastestAperture(Double fastestAperture) {
        this.fastestAperture = fastestAperture;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LensEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LensEditBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LensEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public LensEditBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
