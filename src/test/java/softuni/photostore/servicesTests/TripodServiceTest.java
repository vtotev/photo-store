package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import softuni.photostore.model.binding.TripodAddBindingModel;
import softuni.photostore.model.binding.TripodBrandAddBindingModel;
import softuni.photostore.model.binding.TripodEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.bags.BagBrand;
import softuni.photostore.model.entity.tripods.TripodBrand;
import softuni.photostore.model.entity.tripods.TripodModel;
import softuni.photostore.model.service.TripodFilterModel;
import softuni.photostore.model.view.BagViewModel;
import softuni.photostore.model.view.TripodManageViewModel;
import softuni.photostore.model.view.TripodViewModel;
import softuni.photostore.repository.TripodBrandRepository;
import softuni.photostore.repository.TripodRepository;
import softuni.photostore.service.TripodService;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.impl.TripodServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TripodServiceTest {
    @Mock
    private TripodBrandRepository mockedBrand;
    @Mock
    private TripodRepository  mockedModel;
    @Mock
    private PictureService mockedPictures;
    @Mock
    private ModelMapper mockedMapper;

    private TripodService sut;

    @BeforeEach
    public void setup() {
        this.sut = new TripodServiceImpl(mockedModel, mockedBrand, mockedPictures, mockedMapper);
    }

    // FindByBrandName
    @Test
    public void testFindByBrandName() {
        TripodBrand brand = new TripodBrand();

        brand
                .setTripods(List.of(new TripodModel()))
                .setBrandName("brand")
                .setId("test");

        assertThat(brand.getId()).isEqualTo("test");
        assertThat(brand.getBrandName()).isEqualTo("brand");
        assertThat(brand.getTripods().size()).isEqualTo(1);

        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(brand));
        TripodBrand test = sut.getBrandByName("test");
        assertThat(test.getBrandName()).isEqualTo(brand.getBrandName());
    }

    @Test
    public void testFindByBrandNameNotFound() {
        TripodBrand test = sut.getBrandByName("test");
        assertThat(test).isNull();
    }

    // registerNewBrand
    @Test
    public void testRegisterNewBrand() {
        assertThat(sut.registerNewBrand(new TripodBrandAddBindingModel())).isTrue();
    }

    @Test
    public void testRegisterNewBrandFailed() {
        TripodBrandAddBindingModel newTripodBrand = new TripodBrandAddBindingModel();
        newTripodBrand.setBrandName("test");
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(TripodBrand.class)));
        assertThat(sut.registerNewBrand(newTripodBrand)).isFalse();
    }

    @Test
    public void testGetAllBrands() {
        Mockito.when(mockedBrand.findAll()).thenReturn(List.of(new TripodBrand("cam1"), new TripodBrand("cam2")));
        assertThat(sut.getAllBrands().size()).isEqualTo(2);
        assertThat(sut.getAllBrands().get(0).getBrandName()).isEqualTo("cam1");
        assertThat(sut.getAllBrands().get(1).getBrandName()).isEqualTo("cam2");
    }

    // getAllBrands
    @Test
    public void testGetAllBrandsEmpty() {
        assertThat(sut.getAllBrands()).isEmpty();
    }

    // addNewTripod
    @Test
    public void testAddNewTripod() {
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.of(new TripodBrand()));
        MockMultipartFile pic = new MockMultipartFile("test", new byte[0]);
        TripodAddBindingModel newTripod = new TripodAddBindingModel();
        newTripod.setBrand("test")
                .setModelName("testModel")
                .setPictures(pic)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setDescription("test description");

        assertThat(newTripod.getBrand()).isEqualTo("test");
        assertThat(newTripod.getModelName()).isEqualTo("testModel");
        assertThat(newTripod.getDescription()).isEqualTo("test description");
        assertThat(newTripod.getQuantity()).isEqualTo(1);
        assertThat(newTripod.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(newTripod, TripodModel.class))
                .thenReturn(new TripodModel());

        assertThat(sut.addNewTripod(newTripod)).isTrue();
    }

    // getAllTripodsByTypeForOverview
    @Test
    public void testGetAllTripodsByTypeForOverview() {
        Mockito.when(mockedModel.findAll())
                .thenReturn(List.of(new TripodModel(), new TripodModel()));
        assertThat(sut.getAllTripodsOverviewModel().size()).isEqualTo(2);
    }

    // editTripod
    @Test
    public void testEditTripodModel() {
        TripodBrand brand = Mockito.mock(TripodBrand.class);

        PictureEntity picture = Mockito.mock(PictureEntity.class);
        Mockito.when(picture.getId())
                .thenReturn("picId");

        TripodModel tripodModel = new TripodModel();
        tripodModel
                .setBrand(brand)
                .setPictures(picture)
                .setId("test")
                .setModelName("test")
                .setPictures(picture)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setDescription("test description");

        assertThat(tripodModel.getId()).isEqualTo("test");
        assertThat(tripodModel.getBrand()).isEqualTo(brand);
        assertThat(tripodModel.getModelName()).isEqualTo("test");
        assertThat(tripodModel.getDescription()).isEqualTo("test description");
        assertThat(tripodModel.getQuantity()).isEqualTo(1);
        assertThat(tripodModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(tripodModel.getPictures()).isEqualTo(picture);

        MockMultipartFile mockedPicture = new MockMultipartFile("test", new byte[0]);

        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(tripodModel));

        Mockito.when(mockedBrand.findByBrandName("brand"))
                .thenReturn(Optional.of(brand));

        Mockito.when(mockedPictures.updatePicture(tripodModel.getPictures(), mockedPicture))
                .thenReturn(picture);

        TripodEditBindingModel tripodEditBindingModel = new TripodEditBindingModel();
        tripodEditBindingModel
                .setId("test")
                .setBrand("brand")
                .setModelName("test")
                .setPictures(mockedPicture)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setDescription("test description");

        assertThat(tripodEditBindingModel.getId()).isEqualTo("test");
        assertThat(tripodEditBindingModel.getBrand()).isEqualTo("brand");
        assertThat(tripodEditBindingModel.getModelName()).isEqualTo("test");
        assertThat(tripodEditBindingModel.getDescription()).isEqualTo("test description");
        assertThat(tripodEditBindingModel.getQuantity()).isEqualTo(1);
        assertThat(tripodEditBindingModel.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(tripodEditBindingModel, TripodModel.class))
                .thenReturn(tripodModel);

        boolean test = sut.editTripod("test", tripodEditBindingModel);
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
        TripodModel tripodModel = new TripodModel();
        tripodModel
                .setId("test")
                .setModelName("test")
                .setPictures(new PictureEntity());
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(tripodModel));
        sut.deleteModelById("test");
        Mockito.verify(mockedModel, Mockito.times(1)).delete(tripodModel);
    }

    // testGetTripodDetailsById
    @Test
    public void testGetTripodDetailsById() {
        TripodModel tripod = Mockito.mock(TripodModel.class);
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(tripod));
        TripodViewModel view = new TripodViewModel();

        view.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand("brand")
                .setDescription("desc")
                .setPictures(null);

        assertThat(view.getId()).isEqualTo("id");
        assertThat(view.getBrand()).isEqualTo("brand");
        assertThat(view.getModelName()).isEqualTo("model");
        assertThat(view.getQuantity()).isEqualTo(1);
        assertThat(view.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(view.getDescription()).isEqualTo("desc");
        assertThat(view.getPictures()).isNull();

        Mockito.when(mockedMapper.map(tripod, TripodViewModel.class))
                .thenReturn(view);
        TripodViewModel testTripod = sut.getTripodDetails("test");
        assertThat(testTripod).isNotNull();
        assertThat(testTripod).isInstanceOf(TripodViewModel.class);
    }

    // testGetAllTripodsForManagement
    @Test
    public void testGetAllTripodsForManagement() {
        Mockito.when(mockedModel.findAll())
                .thenReturn(List.of(new TripodModel(), new TripodModel()));

        TripodManageViewModel view = new TripodManageViewModel();
        view.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand("brand");

        assertThat(view.getId()).isEqualTo("id");
        assertThat(view.getBrand()).isEqualTo("brand");
        assertThat(view.getModelName()).isEqualTo("model");
        assertThat(view.getQuantity()).isEqualTo(1);
        assertThat(view.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(Mockito.any(TripodModel.class), Mockito.eq(TripodManageViewModel.class)))
                .thenReturn(view);

        List<TripodManageViewModel> testTripods = sut.getAllTripodsForManagement();
        assertThat(testTripods).isNotNull();
        assertThat(testTripods).isInstanceOf(ArrayList.class);
        assertThat(testTripods.size()).isEqualTo(2);
    }

    // testGetAllTripodsByFilterCriteria
    @Test
    public void testGetAllTripodsByFilterCriteria() {
        TripodFilterModel filter = new TripodFilterModel();
        filter.setBrand(null)
                .setPriceFrom(null)
                .setPriceTo(null);

        Mockito.when(mockedModel.findAllByFilterCriteria(null, null, null))
                .thenReturn(List.of(new TripodModel(), new TripodModel()));

        Mockito.when(mockedMapper.map(Mockito.any(TripodModel.class), Mockito.eq(TripodViewModel.class)))
                .thenReturn(Mockito.mock(TripodViewModel.class));

        List<TripodViewModel> testTripods = sut.getAllTripodsByFilterCriteria(filter);
        assertThat(testTripods).isNotNull();
        assertThat(testTripods).isInstanceOf(ArrayList.class);
        assertThat(testTripods.size()).isEqualTo(2);
    }
}