package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.cameras.CameraBrand;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraBrandRepository extends JpaRepository<CameraBrand, String> {
    Optional<CameraBrand> findByBrandName(String brandName);

    @Query("select b from CameraBrand b join fetch b.cameraModels m order by m.price desc")
    List<CameraBrand> findCameraBrandsMostExpensiveModels();
}
