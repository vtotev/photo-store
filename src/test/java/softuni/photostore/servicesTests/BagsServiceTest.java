package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import softuni.photostore.model.binding.BagAddBindingModel;
import softuni.photostore.model.binding.BagBrandAddBindingModel;
import softuni.photostore.model.binding.BagEditBindingModel;
import softuni.photostore.model.entity.PictureEntity;
import softuni.photostore.model.entity.bags.BagBrand;
import softuni.photostore.model.entity.bags.BagModel;
import softuni.photostore.model.entity.enums.BagTypeEnum;
import softuni.photostore.model.service.BagFilterModel;
import softuni.photostore.model.view.BagManageViewModel;
import softuni.photostore.model.view.BagViewModel;
import softuni.photostore.repository.BagBrandRepository;
import softuni.photostore.repository.BagRepository;
import softuni.photostore.service.BagsService;
import softuni.photostore.service.PictureService;
import softuni.photostore.service.impl.BagsServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BagsServiceTest {
    @Mock
    private BagBrandRepository mockedBrand;
    @Mock
    private BagRepository mockedModel;
    @Mock
    private PictureService mockedPictures;
    @Mock
    private ModelMapper mockedMapper;
    private BagsService sut;

    @BeforeEach
    public void setup() {
        this.sut = new BagsServiceImpl(mockedBrand, mockedModel, mockedPictures, mockedMapper);
    }

    // FindByBrandName
    @Test
    public void testFindByBrandName() {
        BagBrand brand = new BagBrand();
        brand.setId("testId")
                .setBrandName("brand");
        brand.setBags(List.of(new BagModel()));
        assertThat(brand.getId()).isEqualTo("testId");
        assertThat(brand.getBrandName()).isEqualTo("brand");
        assertThat(brand.getBags().size()).isEqualTo(1);

        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(brand));
        BagBrand test = sut.getBrandByName("test");
        assertThat(test.getBrandName()).isEqualTo(brand.getBrandName());
    }

    @Test
    public void testFindByBrandNameNotFound() {
        BagBrand test = sut.getBrandByName("test");
        assertThat(test).isNull();
    }

    // registerNewBrand
    @Test
    public void testRegisterNewBrand() {
        assertThat(sut.registerNewBrand(new BagBrandAddBindingModel())).isTrue();
    }

    @Test
    public void testRegisterNewBrandFailed() {
        BagBrandAddBindingModel newBagBrand = new BagBrandAddBindingModel();
        newBagBrand.setBrandName("test");
        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.ofNullable(Mockito.mock(BagBrand.class)));
        assertThat(sut.registerNewBrand(newBagBrand)).isFalse();
    }

    @Test
    public void testGetAllBrands() {
        Mockito.when(mockedBrand.findAll()).thenReturn(List.of(new BagBrand("bag1"), new BagBrand("bag2")));
        assertThat(sut.getAllBrands().size()).isEqualTo(2);
        assertThat(sut.getAllBrands().get(0).getBrandName()).isEqualTo("bag1");
        assertThat(sut.getAllBrands().get(1).getBrandName()).isEqualTo("bag2");
    }

    // getAllBrands
    @Test
    public void testGetAllBrandsEmpty() {
        assertThat(sut.getAllBrands()).isEmpty();
    }

    // addNewBag
    @Test
    public void testAddNewBag() {
        BagAddBindingModel newBagBindingModel = new BagAddBindingModel();
        newBagBindingModel.setBagType(BagTypeEnum.BACKPACK)
                .setBrand("Lowepro")
                .setModelName("test")
                .setDescription("test")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setPictures(new MockMultipartFile("test", new byte[0]));

        assertThat(newBagBindingModel.getBagType()).isEqualTo(BagTypeEnum.BACKPACK);
        assertThat(newBagBindingModel.getBrand()).isEqualTo("Lowepro");
        assertThat(newBagBindingModel.getModelName()).isEqualTo("test");
        assertThat(newBagBindingModel.getQuantity()).isEqualTo(1);
        assertThat(newBagBindingModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(newBagBindingModel.getDescription()).isEqualTo("test");

        Mockito.when(mockedBrand.findByBrandName("test"))
                .thenReturn(Optional.of(new BagBrand()));
        BagAddBindingModel newBag = newBagBindingModel;
        newBag.setBrand("test")
                .setModelName("testModel");
        Mockito.when(mockedMapper.map(newBag, BagModel.class))
                .thenReturn(new BagModel());

        assertThat(sut.addNewBag(newBag)).isTrue();
    }

    // getAllBagsByTypeForOverview
    @Test
    public void testGetAllBagsByTypeForOverview() {
        Mockito.when(mockedModel.findAllByBagType(BagTypeEnum.BAG))
                .thenReturn(List.of(new BagModel(), new BagModel()));
        assertThat(sut.getAllBagsByTypeForOverview("bag").size()).isEqualTo(2);
        assertThat(sut.getAllBagsByTypeForOverview("backpack").size()).isEqualTo(0);
    }

    // editBag
    @Test
    public void testEditBagModel() {
        BagBrand brand = Mockito.mock(BagBrand.class);

        PictureEntity picture = Mockito.mock(PictureEntity.class);
        Mockito.when(picture.getId())
                .thenReturn("picId");

        BagModel bagModel = new BagModel();
        bagModel
                .setBagType(BagTypeEnum.BAG)
                .setBrand(brand)
                .setModelName("model")
                .setDescription("desc")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setPictures(picture)
                .setId("testId");

        assertThat(bagModel.getId()).isEqualTo("testId");
        assertThat(bagModel.getBrand()).isEqualTo(brand);
        assertThat(bagModel.getBagType()).isEqualTo(BagTypeEnum.BAG);
        assertThat(bagModel.getModelName()).isEqualTo("model");
        assertThat(bagModel.getDescription()).isEqualTo("desc");
        assertThat(bagModel.getQuantity()).isEqualTo(1);
        assertThat(bagModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(bagModel.getPictures()).isEqualTo(picture);

        assertThat(bagModel.getBagType().getTitle()).isEqualTo("Чанти и калъфи");

        MockMultipartFile mockedPicture = new MockMultipartFile("test", new byte[0]);

        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(bagModel));

        Mockito.when(mockedBrand.findByBrandName("brand"))
                .thenReturn(Optional.of(brand));

        Mockito.when(mockedPictures.updatePicture(bagModel.getPictures(), mockedPicture))
                .thenReturn(picture);

        BagEditBindingModel bagEditBindingModel = new BagEditBindingModel();
        bagEditBindingModel.setId("test")
                .setBrand("brand")
                .setModelName("test")
                .setPictures(mockedPicture)
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBagType(BagTypeEnum.BAG)
                .setDescription("test description");

        assertThat(bagEditBindingModel.getId()).isEqualTo("test");
        assertThat(bagEditBindingModel.getBrand()).isEqualTo("brand");
        assertThat(bagEditBindingModel.getModelName()).isEqualTo("test");
        assertThat(bagEditBindingModel.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(bagEditBindingModel.getQuantity()).isEqualTo(1);
        assertThat(bagEditBindingModel.getBagType()).isEqualTo(BagTypeEnum.BAG);
        assertThat(bagEditBindingModel.getDescription()).isEqualTo("test description");

        Mockito.when(mockedMapper.map(bagEditBindingModel, BagModel.class))
                .thenReturn(bagModel);

        boolean test = sut.editBag("test", bagEditBindingModel);
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
        BagModel bagModel = new BagModel();
        bagModel
                .setId("test")
                .setModelName("test")
                .setPictures(new PictureEntity());
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(bagModel));
        sut.deleteModelById("test");
        Mockito.verify(mockedModel, Mockito.times(1)).delete(bagModel);
    }

    // testGetBagDetailsById
    @Test
    public void testGetBagDetailsById() {
        BagModel bag = Mockito.mock(BagModel.class);
        Mockito.when(mockedModel.findById("test"))
                .thenReturn(Optional.of(bag));
        BagBrand brand = new BagBrand();
        BagViewModel view = new BagViewModel();
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

        Mockito.when(mockedMapper.map(bag, BagViewModel.class))
                .thenReturn(view);
        BagViewModel testBag = sut.getBagDetailsById("test");
        assertThat(testBag).isNotNull();
        assertThat(testBag).isInstanceOf(BagViewModel.class);
    }

    // testGetAllBagsForManagement
    @Test
    public void testGetAllBagsForManagement() {
        Mockito.when(mockedModel.findAll())
                .thenReturn(List.of(new BagModel(), new BagModel()));

        BagBrand brand = new BagBrand();
        BagManageViewModel bag = new BagManageViewModel();
        bag.setId("id")
                .setModelName("model")
                .setQuantity(1)
                .setPrice(BigDecimal.TEN)
                .setBrand(brand);

        assertThat(bag.getId()).isEqualTo("id");
        assertThat(bag.getBrand()).isEqualTo(brand);
        assertThat(bag.getModelName()).isEqualTo("model");
        assertThat(bag.getQuantity()).isEqualTo(1);
        assertThat(bag.getPrice()).isEqualTo(BigDecimal.TEN);

        Mockito.when(mockedMapper.map(Mockito.any(BagModel.class), Mockito.eq(BagManageViewModel.class)))
                .thenReturn(bag);

        List<BagManageViewModel> testBags = sut.getAllBagsForManagement();
        assertThat(testBags).isNotNull();
        assertThat(testBags).isInstanceOf(ArrayList.class);
        assertThat(testBags.size()).isEqualTo(2);
    }

    // testGetAllBagsByFilterCriteria
    @Test
    public void testGetAllBagsByFilterCriteria() {
        BagFilterModel filter = new BagFilterModel();
        filter.setBrand("")
                .setPriceFrom(null)
                .setPriceTo(null);

        Mockito.when(mockedModel.findAllByFilterCriteria("bag", null, null, null))
                .thenReturn(List.of(new BagModel(), new BagModel()));

        Mockito.when(mockedMapper.map(Mockito.any(BagModel.class), Mockito.eq(BagViewModel.class)))
                .thenReturn(Mockito.mock(BagViewModel.class));

        List<BagViewModel> testBags = sut.getAllBagsByFilterCriteria(filter, "bag");
        assertThat(testBags).isNotNull();
        assertThat(testBags).isInstanceOf(ArrayList.class);
        assertThat(testBags.size()).isEqualTo(2);
    }
}