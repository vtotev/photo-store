package softuni.photostore.model.entity.bags;

import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.enums.BagTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bags_models")
public class BagModel extends BaseModel {

    @ManyToOne
    private BagBrand brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BagTypeEnum bagType;

    public BagModel() {
    }

    public BagBrand getBrand() {
        return brand;
    }

    public BagModel setBrand(BagBrand brand) {
        this.brand = brand;
        return this;
    }

    public BagTypeEnum getBagType() {
        return bagType;
    }

    public BagModel setBagType(BagTypeEnum bagType) {
        this.bagType = bagType;
        return this;
    }

}
