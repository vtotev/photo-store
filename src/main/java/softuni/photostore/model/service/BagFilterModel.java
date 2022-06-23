package softuni.photostore.model.service;

public class BagFilterModel {
    private String brand;
    private Integer priceFrom;
    private Integer priceTo;

    public BagFilterModel() {
    }

    public String getBrand() {
        return brand;
    }

    public BagFilterModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public BagFilterModel setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public BagFilterModel setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
        return this;
    }
}