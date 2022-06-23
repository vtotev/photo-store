package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.entity.enums.LensMountTypeEnum;
import softuni.photostore.model.entity.lenses.LensModel;
import softuni.photostore.model.view.LensViewModel;

import java.util.List;

@Repository
public interface LensRepository extends JpaRepository<LensModel, String> {

    List<LensModel> findAllByCameraTypeCompatibility(LensMountTypeEnum cameraTypeCompatibility);


    @Query(value = "select * from lens_models l where " +
            "(l.camera_type_compatibility = :lensType) and " +
            "(:camBrandId IS NULL or l.camera_brand_compatibility_id = :camBrandId) and " +
            "(:brandId IS NULL or l.brand_id = :brandId) and " +
            "(:sensorSize IS NULL or l.sensor_size_compatibility = :sensorSize) and " +
            "(:maxAperture IS NULL or l.fastest_aperture <= 2.8 ) and " +
            "(:priceFrom IS NULL or l.price >= :priceFrom) and " +
            "(:priceTo IS NULL or l.price <= :priceTo)"
            , nativeQuery = true)
    List<LensModel> findAllByFilterCriteria(@Param("lensType") String lensType,
                                              @Param("brandId") String brandId,
                                              @Param("camBrandId") String camBrandId,
                                              @Param("sensorSize") String sensorSize,
                                              @Param("maxAperture") Boolean maxAperture,
                                              @Param("priceFrom") Integer priceFrom,
                                              @Param("priceTo") Integer priceTo);

    @Query(value = "select * from lens_models order by rand() limit 3", nativeQuery = true)
    List<LensModel> findRandom3Lenses();

}
