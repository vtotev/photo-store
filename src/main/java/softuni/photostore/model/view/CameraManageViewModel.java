package softuni.photostore.model.view;

import softuni.photostore.model.entity.cameras.CameraBrand;

import java.math.BigDecimal;

public class CameraManageViewModel {
    private String id;
    private CameraBrand brand;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;

    public CameraManageViewModel() {
    }

    public String getId() {
        return id;
    }

    public CameraManageViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public CameraBrand getBrand() {
        return brand;
    }

    public CameraManageViewModel setBrand(CameraBrand brand) {
        this.brand = brand;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public CameraManageViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CameraManageViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CameraManageViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
