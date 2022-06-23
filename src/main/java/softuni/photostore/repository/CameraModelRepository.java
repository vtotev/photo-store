package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.view.CameraViewModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraModelRepository extends JpaRepository<CameraModel, String> {

    List<CameraModel> findAllByCameraType(CameraTypeEnum cameraType);


    @Query(value = "select * from camera_models c where " +
            "(c.camera_type = :cameraType) and " +
            "(:brandId IS NULL or c.brand_id = :brandId) and " +
            "(:sensorSize IS NULL or c.sensor_size = :sensorSize) and " +
            "(:priceFrom IS NULL or c.price >= :priceFrom) and " +
            "(:priceTo IS NULL or c.price <= :priceTo)"
            , nativeQuery = true)
    List<CameraModel> findAllByFilterCriteria(@Param("cameraType") String cameraType,
                                                  @Param("brandId") String brandId,
                                                  @Param("sensorSize") String sensorSize,
                                                  @Param("priceFrom") Integer priceFrom,
                                                  @Param("priceTo") Integer priceTo);

    Optional<CameraModel> findById(String id);
}
