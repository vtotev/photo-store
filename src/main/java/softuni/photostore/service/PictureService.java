package softuni.photostore.service;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.PictureEntity;

public interface PictureService {

    PictureEntity addPicture(String title, MultipartFile pictureFile);

    void deletePicture(PictureEntity picture);

    PictureEntity updatePicture(PictureEntity oldPicture, MultipartFile pictureFile);
}
