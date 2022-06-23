package softuni.photostore.model.service;

public class LensFilterModel {
    private String brand;
    private String cameraCompatibility;
    private boolean maxAperture28;
    private String sensorSize;
    private Integer priceFrom;
    private Integer priceTo;

    public String getBrand() {
        return brand;
    }

    public LensFilterModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getSensorSize() {
        return sensorSize;
    }

    public LensFilterModel setSensorSize(String sensorSize) {
        this.sensorSize = sensorSize;
        return this;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public LensFilterModel setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public LensFilterModel setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
        return this;
    }

    public String getCameraCompatibility() {
        return cameraCompatibility;
    }

    public LensFilterModel setCameraCompatibility(String cameraCompatibility) {
        this.cameraCompatibility = cameraCompatibility;
        return this;
    }

    public boolean isMaxAperture28() {
        return maxAperture28;
    }

    public LensFilterModel setMaxAperture28(boolean maxAperture28) {
        this.maxAperture28 = maxAperture28;
        return this;
    }
}
