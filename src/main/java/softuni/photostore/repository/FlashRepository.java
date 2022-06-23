package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.entity.lenses.LensModel;

import java.util.List;

@Repository
public interface FlashRepository extends JpaRepository<FlashModel, String> {

    List<FlashModel> findAllByBrandCompatibility(CameraBrand brandCompatibility);

    @Query(value = "select * from flash_models f where " +
            "(f.brand_compatibility_id = :brandCompatibility) and " +
            "(:flashBrandId IS NULL or f.brand_id = :flashBrandId) and " +
            "(:flashType IS NULL or f.flash_type = :flashType) and " +
            "(:isHSS IS NULL or f.ishss) and " +
            "(:priceFrom IS NULL or f.price >= :priceFrom) and " +
            "(:priceTo IS NULL or f.price <= :priceTo)"
            , nativeQuery = true)
    List<FlashModel> findAllByFilterCriteria(@Param("brandCompatibility") String brandCompatibility,
                                            @Param("flashBrandId") String flashBrandId,
                                            @Param("flashType") String flashType,
                                            @Param("isHSS") Boolean isHSS,
                                            @Param("priceFrom") Integer priceFrom,
                                            @Param("priceTo") Integer priceTo);

    @Query(value = "select * from flash_models order by rand() limit 3", nativeQuery = true)
    List<FlashModel> findRandom3Flashes();

}
