package softuni.photostore.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.repository.PictureRepository;
import softuni.photostore.service.CloudinaryService;
import softuni.photostore.service.PictureService;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }


    @Override
    public PictureEntity addPicture(String title, MultipartFile pictureFile) {
        PictureEntity picture = null;
        if (!pictureFile.isEmpty()) {
            CloudinaryImage pictureUpload;
            try {
                pictureUpload = cloudinaryService.upload(pictureFile);
            } catch (IOException e) {
                return null;
            }
            picture = new PictureEntity();
            picture.setTitle(title)
                    .setPublicId(pictureUpload.getPublicId())
                    .setUrl(pictureUpload.getUrl());
//            picture = pictureRepository.save(picture);
        }

        return picture;
    }

    @Override
    public void deletePicture(PictureEntity picture) {
        if (picture != null) {
            cloudinaryService.delete(picture.getPublicId());
            pictureRepository.delete(picture);
        }
    }

    @Override
    public PictureEntity updatePicture(PictureEntity oldPicture, MultipartFile pictureFile) {
        if (!pictureFile.isEmpty()) {
            String title = oldPicture.getTitle();
//            deletePicture(oldPicture);
            return addPicture(title, pictureFile);
        }
        return oldPicture;
    }
}
