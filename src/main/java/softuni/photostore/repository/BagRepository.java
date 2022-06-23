package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.bags.BagModel;
import softuni.photostore.model.entity.enums.BagTypeEnum;

import java.util.List;

@Repository
public interface BagRepository extends JpaRepository<BagModel, String> {

    List<BagModel> findAllByBagType(BagTypeEnum bagType);

    @Query(value = "select * from bags_models b where " +
            "(b.bag_type = :bagType) and " +
            "(:brandName IS NULL or b.brand_id = :brandName) and " +
            "(:priceFrom IS NULL or b.price >= :priceFrom) and " +
            "(:priceTo IS NULL or b.price <= :priceTo)"
            , nativeQuery = true)
    List<BagModel> findAllByFilterCriteria(@Param("bagType") String bagType,
                                            @Param("brandName") String brandName,
                                            @Param("priceFrom") Integer priceFrom,
                                            @Param("priceTo") Integer priceTo);
    @Query(value = "select * from bags_models order by rand() limit 3", nativeQuery = true)
    List<BagModel> findRandom3Bags();
}
