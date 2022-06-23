package softuni.photostore.servicesTests;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.photostore.service.CloudinaryService;
import softuni.photostore.service.impl.CloudinaryImage;
import softuni.photostore.service.impl.CloudinaryServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CloudinaryTest {

    private Cloudinary cloudinary;
    private CloudinaryService sut;

    @BeforeEach
    public void setup() {
        cloudinary = Mockito.mock(Cloudinary.class);
        sut = new CloudinaryServiceImpl(cloudinary);
    }

    @Test
    public void testCloudinaryImage() {
        CloudinaryImage img = new CloudinaryImage();
        img.setUrl("test")
                .setPublicId("test");
        assertThat(img).isNotNull();
        assertThat(img.getUrl()).isNotNull();
        assertThat(img.getUrl()).isEqualTo("test");;
        assertThat(img.getPublicId()).isNotNull();
        assertThat(img.getPublicId()).isEqualTo("test");;
    }

}
