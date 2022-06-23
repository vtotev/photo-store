package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.lenses.LensBrand;

import java.util.Optional;

@Repository
public interface LensBrandRepository extends JpaRepository<LensBrand, String> {

    Optional<LensBrand> findByBrandName(String brandName);

}
