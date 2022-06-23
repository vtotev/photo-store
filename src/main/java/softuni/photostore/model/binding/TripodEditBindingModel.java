package softuni.photostore.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.PictureEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TripodEditBindingModel {

    private String id;

    @NotBlank
    private String brand;

    @NotBlank
    @Size(min = 3, max = 250)
    private String modelName;

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

    public TripodEditBindingModel() {
    }

    public String getId() {
        return id;
    }

    public TripodEditBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public TripodEditBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public TripodEditBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TripodEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TripodEditBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TripodEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getPictures() {
        return pictures;
    }

    public TripodEditBindingModel setPictures(MultipartFile pictures) {
        this.pictures = pictures;
        return this;
    }
}
