package softuni.photostore.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.enums.FlashModesEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


public class FlashEditBindingModel {

    private String id;

    @NotBlank
    private String brand;

    @NotBlank
    @Size(min = 3, max = 250)
    private String modelName;

    @NotBlank
    private String brandCompatibility;

    @NotNull
    private FlashModesEnum flashType;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @NotBlank
    @Size(min = 10)
    private String description;

    private Boolean isHSS;

    private MultipartFile pictures;

    public FlashEditBindingModel() {
    }

    public String getId() {
        return id;
    }

    public FlashEditBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public FlashEditBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public FlashEditBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getBrandCompatibility() {
        return brandCompatibility;
    }

    public FlashEditBindingModel setBrandCompatibility(String brandCompatibility) {
        this.brandCompatibility = brandCompatibility;
        return this;
    }

    public FlashModesEnum getFlashType() {
        return flashType;
    }

    public FlashEditBindingModel setFlashType(FlashModesEnum flashType) {
        this.flashType = flashType;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public FlashEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public FlashEditBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FlashEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getHSS() {
        return isHSS;
    }

    public FlashEditBindingModel setHSS(Boolean HSS) {
        isHSS = HSS;
        return this;
    }

    public MultipartFile getPictures() {
        return pictures;
    }

    public FlashEditBindingModel setPictures(MultipartFile pictures) {
        this.pictures = pictures;
        return this;
    }
}
