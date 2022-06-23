package softuni.photostore.model.entity.bags;
import softuni.photostore.model.entity.BaseBrand;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bags_brands")
public class BagBrand extends BaseBrand {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<BagModel> bags;

    public BagBrand() {
    }

    public BagBrand(String brandName) {
        this.setBrandName(brandName);
    }

    public List<BagModel> getBags() {
        return bags;
    }

    public BagBrand setBags(List<BagModel> bags) {
        this.bags = bags;
        return this;
    }
}
