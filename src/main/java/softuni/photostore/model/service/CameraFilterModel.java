package softuni.photostore.model.service;

public class CameraFilterModel {
    private String brand;
    private String sensorSize;
    private Integer priceFrom;
    private Integer priceTo;

    public String getBrand() {
        return brand;
    }

    public CameraFilterModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getSensorSize() {
        return sensorSize;
    }

    public CameraFilterModel setSensorSize(String sensorSize) {
        this.sensorSize = sensorSize;
        return this;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public CameraFilterModel setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public CameraFilterModel setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
        return this;
    }
}
