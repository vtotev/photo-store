package softuni.photostore.model.service;

public class TripodFilterModel {
    private String brand;
    private Integer priceFrom;
    private Integer priceTo;

    public TripodFilterModel() {
    }

    public String getBrand() {
        return brand;
    }

    public TripodFilterModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public TripodFilterModel setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public TripodFilterModel setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
        return this;
    }
}