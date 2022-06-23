package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import softuni.photostore.model.binding.LensAddBindingModel;
import softuni.photostore.model.binding.LensBrandAddBindingModel;
import softuni.photostore.model.binding.LensEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.bags.BagBrand;
import softuni.photostore.model.entity.cameras.CameraBrand;
import softuni.photostore.model.entity.enums.CameraSensorSizeEnum;
import softuni.photostore.model.entity.enums.LensMountTypeEnum;
import softuni.photostore.model.entity.enums.LensTypeEnum;
import softuni.photostore.model.entity.lenses.LensBrand;
import softuni.photostore.model.entity.lenses.LensModel;
import softuni.photostore.model.service.LensFilterModel;
import softuni.photostore.model.view.BagViewModel;
import softuni.photostore.model.view.LensManageViewModel;
import softuni.photostore.model.view.LensViewModel;
import softuni.photostore.repository.LensBrandRepository;
import softuni.photostore.repository.LensRepository;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.LensService;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.impl.LensServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LensServiceTest {
    @Mock
    private LensBrandRepository mockedBrand;
    @Mock
    private LensRepository mockedModel;
    @Mock
    private PictureService mockedPictures;
    @Mock
    private ModelMapper mockedMapper;
    private LensService sut;
    @Mock
    private CameraService mockedLensService;

    @BeforeEach
    public void setup() {
        this.sut = new LensServiceImpl(mockedModel, mockedMapper, mockedPictures, mockedBrand, mockedLensService);
    }

    // FindByBrandName
    @Test
    public void testFindByBrandName() {
        LensBrand brand = new LensBrand();
        brand.setLenses(List.of(new LensModel()));
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(brand));
        LensBrand test = sut.getBrandByName("test");
        assertThat(test.getBrandName()).isEqualTo(brand.getBrandName());
        assertThat(test.getLenses().size()).isEqualTo(1);
    }

    @Test
    public void testFindByBrandNameNotFound() {
        LensBrand test = sut.getBrandByName("test");
        assertThat(test).isNull();
    }

    // registerNewBrand
    @Test
    public void testRegisterNewBrand() {
        assertThat(sut.registerNewBrand(new LensBrandAddBindingModel())).isTrue();
    }

    @Test
    public void testRegisterNewBrandFailed() {
        LensBrandAddBindingModel newLensBrand = new LensBrandAddBindingModel();
        newLensBrand.setBrandName("test");
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(LensBrand.class)));
        assertThat(sut.registerNewBrand(newLensBrand)).isFalse();
    }

    @Test
    public void testGetAllBrands() {
        Mockito.when(mockedBrand.findAll()).thenReturn(List.of(new LensBrand("lens1"), new LensBrand("lens2")));
        assertThat(sut.getAllBrands().size()).isEqualTo(2);
        assertThat(sut.getAllBrands().get(0).getBrandName()).isEqualTo("lens1");
        assertThat(sut.getAllBrands().get(1).getBrandName()).isEqualTo("lens2");
    }

    // getAllBrands
    @Test
    public void testGetAllBrandsEmpty() {
        assertThat(sut.getAllBrands()).isEmpty();
    }

    // addNewLens
    @Test
    public void testAddNewLens() {
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.of(new LensBrand()));
        LensAddBindingModel newLens = new LensAddBindingModel();
        MockMultipartFile pic = new MockMultipartFile("test", new byte[0]);
        newLens.setBrand("test")
                .setModelName("testModel")
                .setModelName("test")
                .setPicture(pic)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setCameraTypeCompatibility(LensMountTypeEnum.DSLR)
                .setCameraBrandCompatibility("Nikon")
                .setLensType(LensTypeEnum.ZOOM)
                .setDescription("test description")
                .setFastestAperture(2.8d)
                .setSensorSizeCompatibility(CameraSensorSizeEnum.FULL_FRAME);


        assertThat(newLens.getBrand()).isEqualTo("test");
        assertThat(newLens.getModelName()).isEqualTo("test");
        assertThat(newLens.getDescription()).isEqualTo("test description");
        assertThat(newLens.getQuantity()).isEqualTo(1);
        assertThat(newLens.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(newLens.getCameraTypeCompatibility()).isEqualTo(LensMountTypeEnum.DSLR);
        assertThat(newLens.getCameraBrandCompatibility()).isEqualTo("Nikon");
        assertThat(newLens.getFastestAperture()).isEqualTo(2.8d);
        assertThat(newLens.getSensorSizeCompatibility()).isEqualTo(CameraSensorSizeEnum.FULL_FRAME);
        assertThat(newLens.getPicture()).isEqualTo(pic);
        assertThat(newLens.getLensType()).isEqualTo(LensTypeEnum.ZOOM);

        assertThat(newLens.getCameraTypeCompatibility().getTitle()).isEqualTo("Обективи за DSLR фороапарати");
        assertThat(newLens.getCameraTypeCompatibility().getHref()).isEqualTo("dslr");
        assertThat(newLens.getLensType().getTitle()).isEqualTo("Варио (зуум) обективи");

        Mockito.when(mockedMapper.map(newLens, LensModel.class))
                .thenReturn(new LensModel());

        assertThat(sut.addNewLens(newLens)).isTrue();
    }

    // getAllLenssByTypeForOverview
    @Test
    public void testGetAllLenssByTypeForOverview() {
        Mockito.when(mockedModel.findAllByCameraTypeCompatibility(LensMountTypeEnum.DSLR))
                .thenReturn(List.of(new LensModel(), new LensModel()));
        assertThat(sut.getAllLensesByTypeForOverview("dslr").size()).isEqualTo(2);
        assertThat(sut.getAllLensesByTypeForOverview("mirrorless").size()).isEqualTo(0);
    }

    // editLens
    @Test
    public void testEditLensModel() {
        LensBrand brand = Mockito.mock(LensBrand.class);
        CameraBrand cameraBrand = Mockito.mock(CameraBrand.class);
        PictureEntity picture = new PictureEntity();
        picture.setPublicId("publicId")
                .setUrl("url")
                .setTitle("title")
                .setId("id");

        assertThat(picture.getPublicId()).isEqualTo("publicId");
        assertThat(picture.getId()).isEqualTo("id");
        assertThat(picture.getTitle()).isEqualTo("title");
        assertThat(picture.getUrl()).isEqualTo("url");

//        Mockito.when(picture.getId())
//                .thenReturn("picId");

        LensModel lensModel = new LensModel();
        lensModel
                .setBrand(brand)
                .setLensType(LensTypeEnum.ZOOM)
                .setCameraBrandCompatibility(cameraBrand)
                .setCameraTypeCompatibility(LensMountTypeEnum.DSLR)
                .setFastestAperture(2.8d)
                .setSensorSizeCompatibility(CameraSensorSizeEnum.FULL_FRAME)
                .setModelName("model")
                .setDescription("desc")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setId("id")
                .setPictures(picture);

        assertThat(lensModel.getId()).isEqualTo("id");
        assertThat(lensModel.getBrand()).isEqualTo(brand);
        assertThat(lensModel.getLensType()).isEqualTo(LensTypeEnum.ZOOM);
        assertThat(lensModel.getCameraBrandCompatibility()).isEqualTo(cameraBrand);
        assertThat(lensModel.getCameraTypeCompatibility()).isEqualTo(LensMountTypeEnum.DSLR);
        assertThat(lensModel.getFastestAperture()).isEqualTo(2.8d);
        assertThat(lensModel.getSensorSizeCompatibility()).isEqualTo(CameraSensorSizeEnum.FULL_FRAME);
        assertThat(lensModel.getModelName()).isEqualTo("model");
        assertThat(lensModel.getDescription()).isEqualTo("desc");
        assertThat(lensModel.getQuantity()).isEqualTo(1);
        assertThat(lensModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(lensModel.getPictures()).isEqualTo(picture);

        MockMultipartFile mockedPicture = new MockMultipartFile("test", new byte[0]);

        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(lensModel));

        Mockito.when(mockedBrand.findByBrandName("brand"))
                .thenReturn(Optional.of(brand));

        Mockito.when(mockedPictures.updatePicture(lensModel.getPictures(), mockedPicture))
                .thenReturn(picture);

        LensEditBindingModel lensEditBindingModel = new LensEditBindingModel();
        lensEditBindingModel
                .setId("testId")
                .setBrand("brand")
                .setModelName("test")
                .setPicture(mockedPicture)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setCameraTypeCompatibility(LensMountTypeEnum.DSLR)
                .setCameraBrandCompatibility("Nikon")
                .setLensType(LensTypeEnum.ZOOM)
                .setDescription("test description")
                .setFastestAperture(2.8d)
                .setSensorSizeCompatibility(CameraSensorSizeEnum.FULL_FRAME);

        assertThat(lensEditBindingModel.getId()).isEqualTo("testId");
        assertThat(lensEditBindingModel.getBrand()).isEqualTo("brand");
        assertThat(lensEditBindingModel.getModelName()).isEqualTo("test");
        assertThat(lensEditBindingModel.getDescription()).isEqualTo("test description");
        assertThat(lensEditBindingModel.getQuantity()).isEqualTo(1);
        assertThat(lensEditBindingModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(lensEditBindingModel.getCameraTypeCompatibility()).isEqualTo(LensMountTypeEnum.DSLR);
        assertThat(lensEditBindingModel.getCameraBrandCompatibility()).isEqualTo("Nikon");
        assertThat(lensEditBindingModel.getFastestAperture()).isEqualTo(2.8d);
        assertThat(lensEditBindingModel.getSensorSizeCompatibility()).isEqualTo(CameraSensorSizeEnum.FULL_FRAME);
        assertThat(lensEditBindingModel.getPicture()).isEqualTo(mockedPicture);
        assertThat(lensEditBindingModel.getLensType()).isEqualTo(LensTypeEnum.ZOOM);

        Mockito.when(mockedMapper.map(lensEditBindingModel, LensModel.class))
                .thenReturn(lensModel);

        boolean test = sut.editLens("test", lensEditBindingModel);
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
        LensModel lensModel = new LensModel();
        lensModel
                .setId("test")
                .setModelName("test")
                .setPictures(new PictureEntity());
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(lensModel));
        sut.deleteModelById("test");
        Mockito.verify(mockedModel, Mockito.times(1)).delete(lensModel);
    }

    // testGetLensDetailsById
    @Test
    public void testGetLensDetailsById() {
        LensModel lens = Mockito.mock(LensModel.class);
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(lens));
        LensViewModel view = new LensViewModel();
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

        Mockito.when(mockedMapper.map(lens, LensViewModel.class))
                .thenReturn(view);
        LensViewModel testLens = sut.getLensDetailsById("test");
        assertThat(testLens).isNotNull();
        assertThat(testLens).isInstanceOf(LensViewModel.class);
    }

    // testGetAllLenssForManagement
    @Test
    public void testGetAllLensesForManagement() {
        Mockito.when(mockedModel.findAll())
                .thenReturn(List.of(new LensModel(), new LensModel()));

        LensBrand brand = new LensBrand();
        LensManageViewModel lensForManage = new LensManageViewModel();
        lensForManage.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand(brand);

        assertThat(lensForManage.getId()).isEqualTo("id");
        assertThat(lensForManage.getBrand()).isEqualTo(brand);
        assertThat(lensForManage.getModelName()).isEqualTo("model");
        assertThat(lensForManage.getQuantity()).isEqualTo(1);
        assertThat(lensForManage.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(Mockito.any(LensModel.class), Mockito.eq(LensManageViewModel.class)))
                .thenReturn(lensForManage);

        List<LensManageViewModel> testLenss = sut.getAllLensesForManagement();
        assertThat(testLenss).isNotNull();
        assertThat(testLenss).isInstanceOf(ArrayList.class);
        assertThat(testLenss.size()).isEqualTo(2);
    }

    // testGetAllLenssByFilterCriteria
    @Test
    public void testGetAllLenssByFilterCriteria() {
        LensFilterModel filter = new LensFilterModel();
        filter.setBrand("")
                .setCameraCompatibility(null)
                .setMaxAperture28(true)
                .setSensorSize(null)
                .setPriceFrom(null)
                .setPriceTo(null);

        Mockito.when(mockedModel.findAllByFilterCriteria("prime", null, null, null, true, null, null))
                .thenReturn(List.of(new LensModel(), new LensModel()));

        Mockito.when(mockedMapper.map(Mockito.any(LensModel.class), Mockito.eq(LensViewModel.class)))
                .thenReturn(Mockito.mock(LensViewModel.class));

        List<LensViewModel> testLenss = sut.getAllLensesByFilterCriteria(filter, "prime");
        assertThat(testLenss).isNotNull();
        assertThat(testLenss).isInstanceOf(ArrayList.class);
        assertThat(testLenss.size()).isEqualTo(2);
    }
}