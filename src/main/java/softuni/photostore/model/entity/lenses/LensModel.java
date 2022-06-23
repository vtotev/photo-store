package softuni.photostore.model.entity.lenses;

import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.LensMountTypeEnum;
import softuni.photostore.model.entity.enums.LensTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "lens_models")
public class LensModel extends BaseModel {

    @ManyToOne
    private LensBrand brand;

    @ManyToOne
    private CameraBrand cameraBrandCompatibility;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LensMountTypeEnum cameraTypeCompatibility;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CameraSensorSizeEnum sensorSizeCompatibility;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LensTypeEnum lensType;

    @Column(nullable = false)
    private Double fastestAperture;

    public LensModel() {
    }

    public LensBrand getBrand() {
        return brand;
    }

    public LensModel setBrand(LensBrand brand) {
        this.brand = brand;
        return this;
    }

    public CameraBrand getCameraBrandCompatibility() {
        return cameraBrandCompatibility;
    }

    public LensModel setCameraBrandCompatibility(CameraBrand cameraBrandCompatibility) {
        this.cameraBrandCompatibility = cameraBrandCompatibility;
        return this;
    }

    public LensMountTypeEnum getCameraTypeCompatibility() {
        return cameraTypeCompatibility;
    }

    public LensModel setCameraTypeCompatibility(LensMountTypeEnum cameraTypeCompatibility) {
        this.cameraTypeCompatibility = cameraTypeCompatibility;
        return this;
    }

    public CameraSensorSizeEnum getSensorSizeCompatibility() {
        return sensorSizeCompatibility;
    }

    public LensModel setSensorSizeCompatibility(CameraSensorSizeEnum sensorSizeCompatibility) {
        this.sensorSizeCompatibility = sensorSizeCompatibility;
        return this;
    }

    public LensTypeEnum getLensType() {
        return lensType;
    }

    public LensModel setLensType(LensTypeEnum lensType) {
        this.lensType = lensType;
        return this;
    }

    public Double getFastestAperture() {
        return fastestAperture;
    }

    public LensModel setFastestAperture(Double fastestAperture) {
        this.fastestAperture = fastestAperture;
        return this;
    }

}
