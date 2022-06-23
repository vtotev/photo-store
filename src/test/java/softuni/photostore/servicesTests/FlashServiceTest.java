package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import softuni.photostore.model.binding.FlashAddBindingModel;
import softuni.photostore.model.binding.FlashBrandAddBindingModel;
import softuni.photostore.model.binding.FlashEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.bags.BagBrand;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.enums.FlashModesEnum;
import softuni.photostore.model.entity.flashes.FlashBrand;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.entity.lenses.LensBrand;
import softuni.photostore.model.service.FlashFilterModel;
import softuni.photostore.model.view.BagViewModel;
import softuni.photostore.model.view.FlashManageViewModel;
import softuni.photostore.model.view.FlashViewModel;
import softuni.photostore.repository.FlashBrandRepository;
import softuni.photostore.repository.FlashRepository;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.FlashService;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.impl.FlashServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FlashServiceTest {
    @Mock
    private FlashBrandRepository mockedBrand;
    @Mock
    private FlashRepository mockedModel;
    @Mock
    private PictureService mockedPictures;
    @Mock
    private ModelMapper mockedMapper;
    private FlashService sut;
    @Mock
    private CameraService mockedCameraService;

    @BeforeEach
    public void setup() {
        this.sut = new FlashServiceImpl(mockedBrand, mockedModel, mockedCameraService, mockedPictures, mockedMapper);
    }

    // FindByBrandName
    @Test
    public void testFindByBrandName() {
        softuni.photostore.model.entity.flashes.FlashBrand brand = new softuni.photostore.model.entity.flashes.FlashBrand();
        brand.setFlashes(List.of(new FlashModel()))
                .setId("id")
                .setBrandName("brand");

        assertThat(brand.getId()).isEqualTo("id");
        assertThat(brand.getBrandName()).isEqualTo("brand");
        assertThat(brand.getFlashes().size()).isEqualTo(1);

        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(brand));
        softuni.photostore.model.entity.flashes.FlashBrand test = sut.getBrandByName("test");
        assertThat(test.getBrandName()).isEqualTo(brand.getBrandName());
    }

    @Test
    public void testFindByBrandNameNotFound() {
        softuni.photostore.model.entity.flashes.FlashBrand test = sut.getBrandByName("test");
        assertThat(test).isNull();
    }

    // registerNewBrand
    @Test
    public void testRegisterNewBrand() {
        assertThat(sut.registerNewBrand(new FlashBrandAddBindingModel())).isTrue();
    }

    @Test
    public void testRegisterNewBrandFailed() {
        FlashBrandAddBindingModel newFlashBrand = new FlashBrandAddBindingModel();
        newFlashBrand.setBrandName("test");
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(softuni.photostore.model.entity.flashes.FlashBrand.class)));
        assertThat(sut.registerNewBrand(newFlashBrand)).isFalse();
    }

    @Test
    public void testGetAllBrands() {
        Mockito.when(mockedBrand.findAll()).thenReturn(List.of(new softuni.photostore.model.entity.flashes.FlashBrand("flash1"), new softuni.photostore.model.entity.flashes.FlashBrand("flash2")));
        assertThat(sut.getAllBrands().size()).isEqualTo(2);
        assertThat(sut.getAllBrands().get(0).getBrandName()).isEqualTo("flash1");
        assertThat(sut.getAllBrands().get(1).getBrandName()).isEqualTo("flash2");
    }

    // getAllBrands
    @Test
    public void testGetAllBrandsEmpty() {
        assertThat(sut.getAllBrands()).isEmpty();
    }

    // addNewFlash
    @Test
    public void testAddNewFlash() {
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.of(new softuni.photostore.model.entity.flashes.FlashBrand()));
        FlashAddBindingModel newFlash = new FlashAddBindingModel();
        newFlash.setBrand("test")
                .setModelName("testModel")
                .setDescription("test")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setFlashType(FlashModesEnum.TTL)
                .setBrandCompatibility("test")
                .setHSS(true)
                .setPictures(new MockMultipartFile("test", new byte[0]));

        assertThat(newFlash.getBrand()).isEqualTo("test");
        assertThat(newFlash.getModelName()).isEqualTo("testModel");
        assertThat(newFlash.getDescription()).isEqualTo("test");
        assertThat(newFlash.getQuantity()).isEqualTo(1);
        assertThat(newFlash.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(newFlash.getFlashType()).isEqualTo(FlashModesEnum.TTL);
        assertThat(newFlash.getBrandCompatibility()).isEqualTo("test");
        assertThat(newFlash.getHSS()).isTrue();

        Mockito.when(mockedMapper.map(newFlash, FlashModel.class))
                .thenReturn(new FlashModel());

        assertThat(sut.addNewFlash(newFlash)).isTrue();
    }

    // testGetAllFlashesByBrandCompatibilityForOverview
    @Test
    public void testGetAllFlashesByBrandCompatibilityForOverview() {
        CameraBrand cameraBrand = new CameraBrand();
        cameraBrand.setBrandName("Nikon");
        Mockito.when(mockedCameraService.getBrandByName("Nikon"))
                .thenReturn(cameraBrand);
        Mockito.when(mockedModel.findAllByBrandCompatibility(cameraBrand))
                .thenReturn(List.of(new FlashModel(), new FlashModel()));
        assertThat(sut.getAllFlashesByBrandCompatibilityForOverview("Nikon").size()).isEqualTo(2);
        assertThat(sut.getAllFlashesByBrandCompatibilityForOverview("mirrorless").size()).isEqualTo(0);
    }

    // editFlash
    @Test
    public void testEditFlashModel() {
        softuni.photostore.model.entity.flashes.FlashBrand brand = Mockito.mock(softuni.photostore.model.entity.flashes.FlashBrand.class);
        CameraBrand cameraBrand = Mockito.mock(CameraBrand.class);

        PictureEntity picture = Mockito.mock(PictureEntity.class);
        Mockito.when(picture.getId())
                .thenReturn("picId");

        FlashModel flashModel = new FlashModel();
        flashModel
                .setFlashType(FlashModesEnum.TTL)
                .setHSS(true)
                .setBrand(brand)
                .setBrandCompatibility(cameraBrand)
                .setPictures(picture)
                .setId("id")
                .setModelName("model")
                .setDescription("desc")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN);

        assertThat(flashModel.getId()).isEqualTo("id");
        assertThat(flashModel.getFlashType()).isEqualTo(FlashModesEnum.TTL);
        assertThat(flashModel.getHSS()).isEqualTo(true);
        assertThat(flashModel.getBrand()).isEqualTo(brand);
        assertThat(flashModel.getBrandCompatibility()).isEqualTo(cameraBrand);
        assertThat(flashModel.getModelName()).isEqualTo("model");
        assertThat(flashModel.getDescription()).isEqualTo("desc");
        assertThat(flashModel.getQuantity()).isEqualTo(1);
        assertThat(flashModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(flashModel.getPictures()).isEqualTo(picture);

        MockMultipartFile mockedPicture = new MockMultipartFile("test", new byte[0]);

        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(flashModel));

        Mockito.when(mockedBrand.findByBrandName("brand"))
                .thenReturn(Optional.of(brand));

        Mockito.when(mockedPictures.updatePicture(flashModel.getPictures(), mockedPicture))
                .thenReturn(picture);

        FlashEditBindingModel flashAddBindingModel = new FlashEditBindingModel();
        flashAddBindingModel
                .setId("testId")
                .setBrand("brand")
                .setModelName("testModel")
                .setPictures(mockedPicture)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrandCompatibility("Nikon")
                .setFlashType(FlashModesEnum.TTL)
                .setDescription("test description")
                .setHSS(true);

        assertThat(flashAddBindingModel.getId()).isEqualTo("testId");
        assertThat(flashAddBindingModel.getBrand()).isEqualTo("brand");
        assertThat(flashAddBindingModel.getModelName()).isEqualTo("testModel");
        assertThat(flashAddBindingModel.getDescription()).isEqualTo("test description");
        assertThat(flashAddBindingModel.getQuantity()).isEqualTo(1);
        assertThat(flashAddBindingModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(flashAddBindingModel.getFlashType()).isEqualTo(FlashModesEnum.TTL);
        assertThat(flashAddBindingModel.getBrandCompatibility()).isEqualTo("Nikon");
        assertThat(flashAddBindingModel.getHSS()).isTrue();
        assertThat(flashAddBindingModel.getFlashType().getTitle()).isEqualTo("Автоматична (TTL)");


        Mockito.when(mockedMapper.map(flashAddBindingModel, FlashModel.class))
                .thenReturn(flashModel);

        boolean test = sut.editFlash("test", flashAddBindingModel);
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
        FlashModel flashModel = new FlashModel();
        flashModel
                .setId("test")
                .setModelName("test")
                .setPictures(new PictureEntity());
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(flashModel));
        sut.deleteModelById("test");
        Mockito.verify(mockedModel, Mockito.times(1)).delete(flashModel);
    }

    // testGetFlashDetailsViewById
    @Test
    public void testGetFlashDetailsViewById() {
        FlashModel flash = Mockito.mock(FlashModel.class);
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(flash));
        FlashViewModel view = new FlashViewModel();

        FlashBrand brand = new FlashBrand();
        view.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand(brand)
                .setDescription("desc")
                .setPictures(null);

        assertThat(view.getId()).isEqualTo("id");
        assertThat(view.getBrand()).isEqualTo(brand);
        assertThat(view.getModelName()).isEqualTo("model");
        assertThat(view.getQuantity()).isEqualTo(1);
        assertThat(view.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(view.getDescription()).isEqualTo("desc");
        assertThat(view.getPictures()).isNull();

        Mockito.when(mockedMapper.map(flash, FlashViewModel.class))
                .thenReturn(view);
        FlashViewModel testFlash = sut.getFlashDetailsViewById("test");
        assertThat(testFlash).isNotNull();
        assertThat(testFlash).isInstanceOf(FlashViewModel.class);
    }

    // testGetAllFlashsForManagement
    @Test
    public void testGetAllFlashsForManagement() {
        Mockito.when(mockedModel.findAll())
                .thenReturn(List.of(new FlashModel(), new FlashModel()));

        FlashBrand brand = new FlashBrand();
        FlashManageViewModel view = new FlashManageViewModel();
        view.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand(brand);

        assertThat(view.getId()).isEqualTo("id");
        assertThat(view.getBrand()).isEqualTo(brand);
        assertThat(view.getModelName()).isEqualTo("model");
        assertThat(view.getQuantity()).isEqualTo(1);
        assertThat(view.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(Mockito.any(FlashModel.class), Mockito.eq(FlashManageViewModel.class)))
                .thenReturn(Mockito.mock(FlashManageViewModel.class));

        List<FlashManageViewModel> testFlashs = sut.getAllFlashesForManagement();
        assertThat(testFlashs).isNotNull();
        assertThat(testFlashs).isInstanceOf(ArrayList.class);
        assertThat(testFlashs.size()).isEqualTo(2);
    }

    // testGetAllFlashsByFilterCriteria
    @Test
    public void testGetAllFlashsByFilterCriteria() {
        FlashFilterModel filter = new FlashFilterModel();
        filter.setBrand(null)
                .setFlashType(null)
                .setHSS(true)
                .setPriceFrom(null)
                .setPriceTo(null);

        Mockito.when(mockedCameraService.getBrandByName("Nikon"))
                .thenReturn(Mockito.mock(CameraBrand.class));

        Mockito.when(mockedCameraService.getBrandByName("Nikon").getId())
                .thenReturn("Nikon");

        Mockito.when(mockedModel.findAllByFilterCriteria("Nikon", null, null, true, null, null))
                .thenReturn(List.of(new FlashModel(), new FlashModel()));

        Mockito.when(mockedMapper.map(Mockito.any(FlashModel.class), Mockito.eq(FlashViewModel.class)))
                .thenReturn(Mockito.mock(FlashViewModel.class));

        List<FlashViewModel> testFlashs = sut.getAllFlashesByFilterCriteria(filter, "Nikon");
        assertThat(testFlashs).isNotNull();
        assertThat(testFlashs).isInstanceOf(ArrayList.class);
        assertThat(testFlashs.size()).isEqualTo(2);
    }
}