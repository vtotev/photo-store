package softuni.photostore.model.service;

public class FlashFilterModel {
    private String brand;
    private String flashType;
    private Boolean isHSS;
    private Integer priceFrom;
    private Integer priceTo;

    public FlashFilterModel() {
    }

    public String getBrand() {
        return brand;
    }

    public FlashFilterModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getFlashType() {
        return flashType;
    }

    public FlashFilterModel setFlashType(String flashType) {
        this.flashType = flashType;
        return this;
    }

    public Boolean getHSS() {
        return isHSS;
    }

    public FlashFilterModel setHSS(Boolean HSS) {
        isHSS = HSS;
        return this;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public FlashFilterModel setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public FlashFilterModel setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
        return this;
    }
}