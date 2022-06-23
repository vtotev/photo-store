package softuni.photostore.model.view;

import softuni.photostore.model.entity.lenses.LensBrand;

import java.math.BigDecimal;

public class LensManageViewModel {
    private String id;
    private LensBrand brand;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;

    public String getId() {
        return id;
    }

    public LensManageViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public LensBrand getBrand() {
        return brand;
    }

    public LensManageViewModel setBrand(LensBrand brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public LensManageViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LensManageViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LensManageViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
