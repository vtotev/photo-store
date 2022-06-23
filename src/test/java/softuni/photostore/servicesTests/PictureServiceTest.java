package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.repository.PictureRepository;
import softuni.photostore.service.CloudinaryService;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.impl.CloudinaryImage;
import softuni.photostore.service.impl.PictureServiceImpl;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {

    private PictureService sut;
    @Mock
    private CloudinaryService mockedCloudinaryService;
    @Mock
    private PictureRepository mockedPics;

    @BeforeEach
    public void setup() {
        sut = new PictureServiceImpl(mockedCloudinaryService, mockedPics);
    }

//    @Test
//    public void testAddPicture() throws IOException {
//        CloudinaryImage pic = new CloudinaryImage();
//        pic.setUrl("testUrl")
//                        .setPublicId("testId");
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", new byte[0]);
//        Mockito.when(mockedCloudinaryService.upload(Mockito.any(MultipartFile.class)))
//                .thenReturn(pic);
//        PictureEntity uploaded = sut.addPicture("test", mockMultipartFile);
//        assertThat(uploaded).isNotNull();
////        assertThat(uploaded.getPublicId()).isEqualTo("testId");
//    }

    @Test
    public void testAddPictureUnsuccessful() throws IOException {
        PictureEntity uploaded = sut.addPicture("test", new MockMultipartFile("test", new byte[0]));
        assertThat(uploaded).isNull();
    }
}
