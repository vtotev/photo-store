package softuni.photostore.service;

import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.service.impl.CloudinaryImage;

import java.io.IOException;

public interface CloudinaryService {
    CloudinaryImage upload(MultipartFile multipartFile) throws IOException;
    boolean delete(String publicId);
}
