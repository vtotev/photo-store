package softuni.photostore.model.entity.tripods;

import softuni.photostore.model.entity.BaseBrand;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tripod_brands")
public class TripodBrand extends BaseBrand {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<TripodModel> tripods;

    public TripodBrand() {
    }

    public TripodBrand(String brandName) {
        this.setBrandName(brandName);
    }

    public List<TripodModel> getTripods() {
        return tripods;
    }

    public TripodBrand setTripods(List<TripodModel> tripods) {
        this.tripods = tripods;
        return this;
    }
}
