package softuni.photostore.model.entity.lenses;

import softuni.photostore.model.entity.BaseBrand;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lens_brands")
public class LensBrand extends BaseBrand {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<LensModel> lenses;

    public LensBrand() {
    }

    public LensBrand(String brandName) {
        this.setBrandName(brandName);
    }

    public List<LensModel> getLenses() {
        return lenses;
    }

    public LensBrand setLenses(List<LensModel> lenses) {
        this.lenses = lenses;
        return this;
    }
}
