package softuni.photostore.model.entity.cameras;

import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "camera_models")
public class CameraModel extends BaseModel {

    @ManyToOne
    private CameraBrand brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CameraTypeEnum cameraType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CameraSensorSizeEnum sensorSize;

    @Column(nullable = false)
    private Double megapixels;

    public CameraModel() {
    }

    public CameraBrand getBrand() {
        return brand;
    }

    public CameraModel setBrand(CameraBrand brand) {
        this.brand = brand;
        return this;
    }

    public CameraTypeEnum getCameraType() {
        return cameraType;
    }

    public CameraModel setCameraType(CameraTypeEnum cameraType) {
        this.cameraType = cameraType;
        return this;
    }

    public CameraSensorSizeEnum getSensorSize() {
        return sensorSize;
    }

    public CameraModel setSensorSize(CameraSensorSizeEnum sensorSize) {
        this.sensorSize = sensorSize;
        return this;
    }

    public Double getMegapixels() {
        return megapixels;
    }

    public CameraModel setMegapixels(Double megapixels) {
        this.megapixels = megapixels;
        return this;
    }

}
