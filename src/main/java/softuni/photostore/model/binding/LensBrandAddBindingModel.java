package softuni.photostore.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LensBrandAddBindingModel {
    @NotBlank
    @Size(min = 3, max = 30)
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public LensBrandAddBindingModel setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }
}
