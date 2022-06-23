package softuni.photostore.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TripodBrandAddBindingModel {
    @NotBlank
    @Size(min = 3, max = 30)
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public TripodBrandAddBindingModel setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }
}
