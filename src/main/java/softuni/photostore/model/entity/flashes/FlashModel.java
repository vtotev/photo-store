package softuni.photostore.model.entity.flashes;

import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.entity.enums.FlashModesEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "flash_models")
public class FlashModel extends BaseModel {

    @ManyToOne
    private FlashBrand brand;

    @ManyToOne
    private CameraBrand brandCompatibility;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlashModesEnum flashType;

    @Column(nullable = false)
    private Boolean isHSS;

    public FlashModel() {
    }

    public FlashBrand getBrand() {
        return brand;
    }

    public FlashModel setBrand(FlashBrand brand) {
        this.brand = brand;
        return this;
    }

    public CameraBrand getBrandCompatibility() {
        return brandCompatibility;
    }

    public FlashModel setBrandCompatibility(CameraBrand brandCompatibility) {
        this.brandCompatibility = brandCompatibility;
        return this;
    }

    public FlashModesEnum getFlashType() {
        return flashType;
    }

    public FlashModel setFlashType(FlashModesEnum flashType) {
        this.flashType = flashType;
        return this;
    }

    public Boolean getHSS() {
        return isHSS;
    }

    public FlashModel setHSS(Boolean HSS) {
        isHSS = HSS;
        return this;
    }

}
