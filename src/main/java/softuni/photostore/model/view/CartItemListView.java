package softuni.photostore.model.view;

import java.math.BigDecimal;

public class CartItemListView {

    private String id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private String pictureUrl;

    public CartItemListView() {
    }

    public String getId() {
        return id;
    }

    public CartItemListView setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public CartItemListView setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItemListView setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CartItemListView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public CartItemListView setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
}
