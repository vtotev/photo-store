package softuni.photostore.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart")
public class CartItem extends BaseEntity {

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerIP;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private LocalDateTime dateAdded;

    public CartItem() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public CartItem setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public CartItem setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getCustomerIP() {
        return customerIP;
    }

    public CartItem setCustomerIP(String customerIP) {
        this.customerIP = customerIP;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public CartItem setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductType() {
        return productType;
    }

    public CartItem setProductType(String productType) {
        this.productType = productType;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CartItem setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public CartItem setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public CartItem setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }
}
