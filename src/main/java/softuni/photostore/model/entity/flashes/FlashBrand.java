package softuni.photostore.model.entity.flashes;

import softuni.photostore.model.entity.BaseBrand;
import softuni.photostore.model.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flash_brands")
public class FlashBrand extends BaseBrand {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<FlashModel> flashes;

    public FlashBrand() {
    }

    public FlashBrand(String brandName) {
        this.setBrandName(brandName);
    }

    public List<FlashModel> getFlashes() {
        return flashes;
    }

    public FlashBrand setFlashes(List<FlashModel> flashes) {
        this.flashes = flashes;
        return this;
    }
}
