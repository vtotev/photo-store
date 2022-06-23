package softuni.photostore.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.enums.BagTypeEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BagAddBindingModel {

    @NotBlank
    private String brand;

    @NotBlank
    @Size(min = 3, max = 250)
    private String modelName;

    @NotNull
    private BagTypeEnum bagType;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @NotBlank
    @Size(min = 10)
    private String description;

    private MultipartFile pictures;

    public BagAddBindingModel() {
    }

    public String getBrand() {
        return brand;
    }

    public BagAddBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public BagAddBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BagTypeEnum getBagType() {
        return bagType;
    }

    public BagAddBindingModel setBagType(BagTypeEnum bagType) {
        this.bagType = bagType;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BagAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BagAddBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BagAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getPictures() {
        return pictures;
    }

    public BagAddBindingModel setPictures(MultipartFile pictures) {
        this.pictures = pictures;
        return this;
    }
}
