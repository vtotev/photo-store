package softuni.photostore.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.photostore.model.binding.LensAddBindingModel;
import softuni.photostore.model.binding.LensBrandAddBindingModel;
import softuni.photostore.model.binding.LensEditBindingModel;
import softuni.photostore.model.entity.enums.LensMountTypeEnum;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.entity.lenses.LensModel;
import softuni.photostore.model.service.LensFilterModel;
import softuni.photostore.model.view.LensViewModel;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.CartService;
import softuni.photostore.service.LensService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LensesController {
    private final LensService lensService;
    private final CameraService cameraService;
    private final ModelMapper modelMapper;
    private final CartService cartService;

    public LensesController(LensService lensService, CameraService cameraService, ModelMapper modelMapper, CartService cartService) {
        this.lensService = lensService;
        this.cameraService = cameraService;
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    @GetMapping("/lenses/{type}")
    public String getLensesByType(@PathVariable String type, Model model) {
        List<LensViewModel> allLensesByType = lensService.getAllLensesByTypeForOverview(type);
        model.addAttribute("lenses", allLensesByType);
        model.addAttribute("lensBrands", lensService.getAllBrands());
        model.addAttribute("cameraBrands", cameraService.getAllBrands());
        model.addAttribute("camType", LensMountTypeEnum.valueOf(type.toUpperCase()).getTitle());
        if (!model.containsAttribute("filter")) {
            model.addAttribute("filter", new LensFilterModel().setBrand("").setCameraCompatibility("").setSensorSize(""));
        }
        if (!model.containsAttribute("areLensesAvailable")) {
            model.addAttribute("areLensesAvailable", !allLensesByType.isEmpty());
        }
        return "lenses";
    }

    @PostMapping("/lenses/{type}")
    public String filterLenses(@PathVariable String type, LensFilterModel filterModel, Model model) {
        List<LensViewModel> allLensesByFilterCriteria = lensService.getAllLensesByFilterCriteria(filterModel, type);
        model.addAttribute("lenses", allLensesByFilterCriteria);
        model.addAttribute("lensBrands", lensService.getAllBrands());
        model.addAttribute("camType", LensMountTypeEnum.valueOf(type.toUpperCase()).getTitle());
        model.addAttribute("filter", filterModel);
        model.addAttribute("areLensesAvailable", !allLensesByFilterCriteria.isEmpty());
        return "lenses";
    }

    // LENS BRANDS OPERATIONS

    @GetMapping("/lenses/manage")
    public String manage(Model model) {
        model.addAttribute("brands", lensService.getAllBrands());
        model.addAttribute("lenses", lensService.getAllLensesForManagement());
        if (!model.containsAttribute("newBrand")) {
            model.addAttribute("newBrand", new LensBrandAddBindingModel());
        }
        if (!model.containsAttribute("brandExisting")) {
            model.addAttribute("brandExisting", false);
        }
        if (!model.containsAttribute("brandNotMeetingRequirements")) {
            model.addAttribute("brandNotMeetingRequirements", false);
        }
        if (!model.containsAttribute("brandAddedSuccessfully")) {
            model.addAttribute("brandAddedSuccessfully", false);
        }
        return "lenses-manage";
    }

    @PostMapping("/lenses/manage/brand/add")
    public String addBrand(@Valid LensBrandAddBindingModel brand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean brandExisting = lensService.isBrandExisting(brand.getBrandName());

        if (brandExisting) {
            redirectAttributes.addFlashAttribute("brandExisting", brandExisting);
            return "redirect:/lenses/manage";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandNotMeetingRequirements", true);
            return "redirect:/lenses/manage";
        }
        boolean isRegistered = lensService.registerNewBrand(brand);
        redirectAttributes.addFlashAttribute("brandAddedSuccessfully", isRegistered);
        return "redirect:/lenses/manage";
    }

    @PostMapping("lenses/manage/brand/delete/{id}")
    public String deleteBrand(@PathVariable String id) {
        lensService.deleteBrandWithId(id);
        return "redirect:/lenses/manage";
    }


    // LENS MODELS OPERATIONS

    @GetMapping("/lenses/manage/model/add")
    public String addLens(Model model) {
        model.addAttribute("brands", lensService.getAllBrands());
        model.addAttribute("brandsCompatibility", cameraService.getAllBrands());
        if (!model.containsAttribute("noPictureSelected")) {
            model.addAttribute("noPictureSelected", false);
        }
        if (!model.containsAttribute("lensModel")) {
            model.addAttribute("lensModel", new LensAddBindingModel());
        }
        return "lenses-add";
    }

    @PostMapping("/lenses/manage/model/add")
    public String addLensConfirm(@Valid LensAddBindingModel lensAdd,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || lensAdd.getPicture().isEmpty()) {
            redirectAttributes.addFlashAttribute("lensModel", lensAdd);
            redirectAttributes.addFlashAttribute("noPictureSelected", lensAdd.getPicture().isEmpty());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.lensModel", bindingResult);
            return "redirect:/lenses/manage/model/add";
        }

        boolean result = lensService.addNewLens(lensAdd);

        return "redirect:/lenses/manage";
    }

    @PostMapping("/lenses/manage/model/delete/{id}")
    public String deleteLensModel(@PathVariable String id) {
        lensService.deleteModelById(id);
        return "redirect:/lenses/manage";
    }

    @GetMapping("/lenses/manage/edit/{id}")
    public String editLens(@PathVariable String id, Model model) {
        model.addAttribute("brands", lensService.getAllBrands());
        if (!model.containsAttribute("lensModel")) {
            LensModel lensById = lensService.getLensById(id);
            LensEditBindingModel editModel = modelMapper.map(lensById, LensEditBindingModel.class);
            editModel.setBrand(lensById.getBrand().getBrandName());
            model.addAttribute("lensModel", editModel)
                    .addAttribute("brandsCompatibility", cameraService.getAllBrands());
        }
        return "lenses-edit";
    }

    @PostMapping("/lenses/manage/edit/{id}")
    public String editLensConfirm(@PathVariable String id,
                                    @Valid LensEditBindingModel editModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("lensModel", editModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.lensModel", bindingResult);
            return "redirect:/lenses/manage/edit/" + id;
        }

        boolean result = lensService.editLens(id, editModel);

        return "redirect:/lenses/manage";
    }

    // LENS DETAILS VIEW

    @GetMapping("/lenses/details/{id}")
    public String showLensDetails(@PathVariable String id, Model model) {
        model.addAttribute("lens", lensService.getLensDetailsById(id));
        return "lenses-details";
    }

    @PostMapping("/lenses/details/{id}")
    public String addToCart(@PathVariable String id, @CurrentSecurityContext SecurityContext context) {
        cartService.addItemToCart(lensService.getLensById(id), context, LensModel.class);
        return "redirect:/lenses/details/" + id;
    }
}
