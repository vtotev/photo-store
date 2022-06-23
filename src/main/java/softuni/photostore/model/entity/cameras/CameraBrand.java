package softuni.photostore.model.entity.cameras;

import softuni.photostore.model.entity.BaseBrand;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "camera_brands")
public class CameraBrand extends BaseBrand {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<CameraModel> cameraModels;

    public CameraBrand() {
    }

    public CameraBrand(String brandName) {
        this.setBrandName(brandName);
    }
    public List<CameraModel> getCameraModels() {
        return cameraModels;
    }

    public CameraBrand setCameraModels(List<CameraModel> cameraModels) {
        this.cameraModels = cameraModels;
        return this;
    }
}
