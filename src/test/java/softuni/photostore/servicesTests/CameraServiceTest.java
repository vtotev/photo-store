package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import softuni.photostore.model.binding.CameraAddBindingModel;
import softuni.photostore.model.binding.CameraBrandAddBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.service.CameraFilterModel;
import softuni.photostore.model.view.CameraManageViewModel;
import softuni.photostore.model.view.CameraViewModel;
import softuni.photostore.repository.CameraBrandRepository;
import softuni.photostore.repository.CameraRepository;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.impl.CameraServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CameraServiceTest {
    @Mock
    private CameraBrandRepository mockedBrand;
    @Mock
    private CameraRepository mockedModel;
    @Mock
    private PictureService mockedPictures;
    @Mock
    private ModelMapper mockedMapper;
    private CameraService sut;

    @BeforeEach
    public void setup() {
        this.sut = new CameraServiceImpl(mockedModel, mockedBrand, mockedPictures, mockedMapper);
    }

    // FindByBrandName
    @Test
    public void testFindByBrandName() {
        CameraBrand brand = new CameraBrand();
        brand.setCameraModels(List.of(new CameraModel()));
        assertThat(brand.getCameraModels().size()).isEqualTo(1);
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(brand));
        CameraBrand test = sut.getBrandByName("test");
        assertThat(test.getBrandName()).isEqualTo(brand.getBrandName());
    }

    @Test
    public void testFindByBrandNameNotFound() {
        CameraBrand test = sut.getBrandByName("test");
        assertThat(test).isNull();
    }

    // registerNewBrand
    @Test
    public void testRegisterNewBrand() {
        assertThat(sut.registerNewBrand(new CameraBrandAddBindingModel())).isTrue();
    }

    @Test
    public void testRegisterNewBrandFailed() {
        CameraBrandAddBindingModel newCameraBrand = new CameraBrandAddBindingModel();
        newCameraBrand.setBrandName("test");
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(CameraBrand.class)));
        assertThat(sut.registerNewBrand(newCameraBrand)).isFalse();
    }

    @Test
    public void testGetAllBrands() {
        Mockito.when(mockedBrand.findAll()).thenReturn(List.of(new CameraBrand("cam1"), new CameraBrand("cam2")));
        assertThat(sut.getAllBrands().size()).isEqualTo(2);
        assertThat(sut.getAllBrands().get(0).getBrandName()).isEqualTo("cam1");
        assertThat(sut.getAllBrands().get(1).getBrandName()).isEqualTo("cam2");
    }

    // getAllBrands
    @Test
    public void testGetAllBrandsEmpty() {
        assertThat(sut.getAllBrands()).isEmpty();
    }

    // addNewCamera
    @Test
    public void testAddNewCamera() {
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.of(new CameraBrand()));
        CameraAddBindingModel newCamera = new CameraAddBindingModel();
        newCamera.setBrand("test")
                .setCameraType(CameraTypeEnum.DSLR)
                .setModelName("testModel")
                .setDescription("test")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setMegapixels(24d)
                .setSensorSize(CameraSensorSizeEnum.FULL_FRAME)
                .setId("test");

        assertThat(newCamera.getId()).isEqualTo("test");
        assertThat(newCamera.getBrand()).isEqualTo("test");
        assertThat(newCamera.getModelName()).isEqualTo("testModel");
        assertThat(newCamera.getDescription()).isEqualTo("test");
        assertThat(newCamera.getQuantity()).isEqualTo(1);
        assertThat(newCamera.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(newCamera.getMegapixels()).isEqualTo(24d);
        assertThat(newCamera.getSensorSize()).isEqualTo(CameraSensorSizeEnum.FULL_FRAME);
        assertThat(newCamera.getCameraType()).isEqualTo(CameraTypeEnum.DSLR);

        Mockito.when(mockedMapper.map(newCamera, CameraModel.class))
                .thenReturn(new CameraModel());

        assertThat(sut.addNewCamera(newCamera)).isTrue();
    }

    // getAllCamerasByTypeForOverview
    @Test
    public void testGetAllCamerasByTypeForOverview() {
        Mockito.when(mockedModel.findAllByCameraType(CameraTypeEnum.DSLR))
                .thenReturn(List.of(new CameraModel(), new CameraModel()));
        assertThat(sut.getAllCamerasByType("dslr").size()).isEqualTo(2);
        assertThat(sut.getAllCamerasByType("mirrorless").size()).isEqualTo(0);
    }

    // editCamera
    @Test
    public void testEditCameraModel() {
        CameraBrand brand = Mockito.mock(CameraBrand.class);

        PictureEntity picture = Mockito.mock(PictureEntity.class);
        Mockito.when(picture.getId())
                .thenReturn("picId");

        CameraModel cameraModel = new CameraModel();
        cameraModel.setBrand(brand)
                .setCameraType(CameraTypeEnum.DSLR)
                .setMegapixels(24d)
                .setSensorSize(CameraSensorSizeEnum.FULL_FRAME)
                .setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setDescription("desc")
                .setPictures(picture);

        assertThat(cameraModel.getBrand()).isEqualTo(brand);
        assertThat(cameraModel.getModelName()).isEqualTo("model");
        assertThat(cameraModel.getDescription()).isEqualTo("desc");
        assertThat(cameraModel.getQuantity()).isEqualTo(1);
        assertThat(cameraModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(cameraModel.getId()).isEqualTo("id");
        assertThat(cameraModel.getMegapixels()).isEqualTo(24d);
        assertThat(cameraModel.getSensorSize()).isEqualTo(CameraSensorSizeEnum.FULL_FRAME);
        assertThat(cameraModel.getCameraType()).isEqualTo(CameraTypeEnum.DSLR);
        assertThat(cameraModel.getPictures()).isEqualTo(picture);
        assertThat(CameraSensorSizeEnum.FULL_FRAME.getDisplayName()).isEqualTo("Full Frame");
        assertThat(CameraTypeEnum.DSLR.getTitle()).isEqualTo("DSLR фороапарати");
        assertThat(CameraTypeEnum.DSLR.getHref()).isEqualTo("dslr");

        MockMultipartFile mockedPicture = new MockMultipartFile("test", new byte[0]);

        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(cameraModel));

        Mockito.when(mockedBrand.findByBrandName("brand"))
                .thenReturn(Optional.of(brand));

        Mockito.when(mockedPictures.updatePicture(cameraModel.getPictures(), mockedPicture))
                .thenReturn(picture);

        CameraAddBindingModel cameraAddBindingModel = new CameraAddBindingModel();
        cameraAddBindingModel.setId("test");
        cameraAddBindingModel
                .setBrand("brand")
                .setPicture(mockedPicture)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setCameraType(CameraTypeEnum.DSLR)
                .setDescription("test description");

        Mockito.when(mockedMapper.map(cameraAddBindingModel, CameraModel.class))
                .thenReturn(cameraModel);

        boolean test = sut.editCamera("test", cameraAddBindingModel);
        assertThat(test).isTrue();
    }

    // testDeleteBrandWithId
    @Test
    public void testDeleteBrandWithId() {
        sut.deleteBrandWithId("test");
        Mockito.verify(mockedBrand, Mockito.times(1)).deleteById("test");
    }

    // testDeleteModelById
    @Test
    public void testDeleteModelById() {
        CameraModel cameraModel = new CameraModel();
        cameraModel
                .setId("test")
                .setModelName("test")
                .setPictures(new PictureEntity());
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(cameraModel));
        sut.deleteModelById("test");
        Mockito.verify(mockedModel, Mockito.times(1)).delete(cameraModel);
    }

    // testGetCameraDetailsById
    @Test
    public void testGetCameraDetailsById() {
        CameraModel camera = Mockito.mock(CameraModel.class);
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(camera));
        CameraViewModel view = new CameraViewModel();

        view.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setDescription("desc")
                .setPictures(null);

        assertThat(view.getId()).isEqualTo("id");
        assertThat(view.getModelName()).isEqualTo("model");
        assertThat(view.getQuantity()).isEqualTo(1);
        assertThat(view.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(view.getDescription()).isEqualTo("desc");
        assertThat(view.getPictures()).isNull();

        Mockito.when(mockedMapper.map(camera, CameraViewModel.class))
                .thenReturn(view);
        CameraViewModel testCamera = sut.getCameraDetailsById("test");
        assertThat(testCamera).isNotNull();
        assertThat(testCamera).isInstanceOf(CameraViewModel.class);
    }

    // testGetAllCamerasForManagement
    @Test
    public void testGetAllCamerasForManagement() {
        Mockito.when(mockedModel.findAll())
                .thenReturn(List.of(new CameraModel(), new CameraModel()));

        CameraBrand brand = new CameraBrand();
        CameraManageViewModel view = new CameraManageViewModel();
        view
                .setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand(brand);

        assertThat(view.getId()).isEqualTo("id");
        assertThat(view.getBrand()).isEqualTo(brand);
        assertThat(view.getModelName()).isEqualTo("model");
        assertThat(view.getQuantity()).isEqualTo(1);
        assertThat(view.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(Mockito.any(CameraModel.class), Mockito.eq(CameraManageViewModel.class)))
                .thenReturn(view);

        List<CameraManageViewModel> testCameras = sut.getAllCamerasForManagement();
        assertThat(testCameras).isNotNull();
        assertThat(testCameras).isInstanceOf(ArrayList.class);
        assertThat(testCameras.size()).isEqualTo(2);
    }

    // testGetAllCamerasByFilterCriteria
    @Test
    public void testGetAllCamerasByFilterCriteria() {
        CameraFilterModel filter = new CameraFilterModel();
        filter.setBrand("")
                .setSensorSize("FULL_FRAME")
                .setPriceFrom(null)
                .setPriceTo(null);

        Mockito.when(mockedModel.findAllByFilterCriteria("dslr", null, "FULL_FRAME", null, null))
                .thenReturn(List.of(new CameraModel(), new CameraModel()));

        Mockito.when(mockedMapper.map(Mockito.any(CameraModel.class), Mockito.eq(CameraViewModel.class)))
                .thenReturn(Mockito.mock(CameraViewModel.class));

        List<CameraViewModel> testCameras = sut.getAllCamerasByFilterCriteria(filter, "dslr");
        assertThat(testCameras).isNotNull();
        assertThat(testCameras).isInstanceOf(ArrayList.class);
        assertThat(testCameras.size()).isEqualTo(2);
    }
}