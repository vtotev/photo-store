package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.PictureEntity;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, String> {


}
