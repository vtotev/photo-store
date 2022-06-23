package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.bags.BagBrand;

import java.util.Optional;

@Repository
public interface BagBrandRepository extends JpaRepository<BagBrand, String> {

    Optional<BagBrand> findByBrandName(String brandName);

}
