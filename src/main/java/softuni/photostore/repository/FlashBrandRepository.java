package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.flashes.FlashBrand;

import java.util.Optional;

@Repository
public interface FlashBrandRepository extends JpaRepository<FlashBrand, String> {
    Optional<FlashBrand> findByBrandName(String brandName);
}
