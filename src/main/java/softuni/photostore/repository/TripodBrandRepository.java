package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.tripods.TripodBrand;

import java.util.Optional;

@Repository
public interface TripodBrandRepository extends JpaRepository <TripodBrand, String> {
    Optional<TripodBrand> findByBrandName(String brandName);
}
