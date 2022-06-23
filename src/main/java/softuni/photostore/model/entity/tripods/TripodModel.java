package softuni.photostore.model.entity.tripods;

import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.PictureEntity;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tripod_models")
public class TripodModel extends BaseModel {

    @ManyToOne
    private TripodBrand brand;

    public TripodModel() {
    }

    public TripodBrand getBrand() {
        return brand;
    }

    public TripodModel setBrand(TripodBrand brand) {
        this.brand = brand;
        return this;
    }

}
