package softuni.photostore.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.enums.BagTypeEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BagEditBindingModel {

    private String id;

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

    public BagEditBindingModel() {
    }

    public String getId() {
        return id;
    }

    public BagEditBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public BagEditBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public BagEditBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BagTypeEnum getBagType() {
        return bagType;
    }

    public BagEditBindingModel setBagType(BagTypeEnum bagType) {
        this.bagType = bagType;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BagEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BagEditBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BagEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getPictures() {
        return pictures;
    }

    public BagEditBindingModel setPictures(MultipartFile pictures) {
        this.pictures = pictures;
        return this;
    }
}
