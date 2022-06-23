package softuni.photostore.model.view;

import softuni.photostore.model.entity.bags.BagBrand;
import java.math.BigDecimal;

public class BagManageViewModel {

    private String id;
    private BagBrand brand;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;

    public BagManageViewModel() {
    }

    public String getId() {
        return id;
    }

    public BagManageViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public BagBrand getBrand() {
        return brand;
    }

    public BagManageViewModel setBrand(BagBrand brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public BagManageViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BagManageViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BagManageViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
