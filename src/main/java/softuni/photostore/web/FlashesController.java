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
import softuni.photostore.model.binding.FlashAddBindingModel;
import softuni.photostore.model.binding.FlashBrandAddBindingModel;
import softuni.photostore.model.binding.FlashEditBindingModel;
import softuni.photostore.model.entity.bags.BagModel;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.service.FlashFilterModel;
import softuni.photostore.model.view.FlashViewModel;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.CartService;
import softuni.photostore.service.FlashService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FlashesController {
    private final FlashService flashService;
    private final CameraService cameraService;
    private final ModelMapper modelMapper;
    private final CartService cartService;

    public FlashesController(FlashService flashService, CameraService cameraService, ModelMapper modelMapper, CartService cartService) {
        this.flashService = flashService;
        this.cameraService = cameraService;
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    @GetMapping("/flashes/{camBrand}")
    public String getFlashesByBrandCompatibility(@PathVariable String camBrand, Model model) {
        List<FlashViewModel> allFlashesByBrandCompatibility = flashService.getAllFlashesByBrandCompatibilityForOverview(camBrand);
        model.addAttribute("flashes", allFlashesByBrandCompatibility);
        model.addAttribute("flashBrands", flashService.getAllBrands());
        model.addAttribute("cameraBrands", cameraService.getAllBrands());
        if (!model.containsAttribute("filter")) {
            model.addAttribute("filter", new FlashFilterModel().setBrand("").setFlashType(""));
        }
        if (!model.containsAttribute("areFlashesAvailable")) {
            model.addAttribute("areFlashesAvailable", !allFlashesByBrandCompatibility.isEmpty());
        }
        return "flashes";
    }

    @PostMapping("/flashes/{camBrand}")
    public String filterFlashes(@PathVariable String camBrand, FlashFilterModel filterModel, Model model) {
        List<FlashViewModel> allFlashesByFilterCriteria = flashService.getAllFlashesByFilterCriteria(filterModel, camBrand);
        model.addAttribute("flashes", allFlashesByFilterCriteria);
        model.addAttribute("flashBrands", flashService.getAllBrands());
        model.addAttribute("filter", filterModel);
        model.addAttribute("areFlashesAvailable", !allFlashesByFilterCriteria.isEmpty());
        return "flashes";
    }

    // FLASH BRANDS OPERATIONS

    @GetMapping("/flashes/manage")
    public String manage(Model model) {
        model.addAttribute("brands", flashService.getAllBrands());
        model.addAttribute("flashes", flashService.getAllFlashesForManagement());
        if (!model.containsAttribute("newBrand")) {
            model.addAttribute("newBrand", new FlashBrandAddBindingModel());
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
        return "flashes-manage";
    }

    @PostMapping("/flashes/manage/brand/add")
    public String addBrand(@Valid FlashBrandAddBindingModel brand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean brandExisting = flashService.isBrandExisting(brand.getBrandName());

        if (brandExisting) {
            redirectAttributes.addFlashAttribute("brandExisting", brandExisting);
            return "redirect:/flashes/manage";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandNotMeetingRequirements", true);
            return "redirect:/flashes/manage";
        }
        boolean isRegistered = flashService.registerNewBrand(brand);
        redirectAttributes.addFlashAttribute("brandAddedSuccessfully", isRegistered);
        return "redirect:/flashes/manage";
    }

    @PostMapping("flashes/manage/brand/delete/{id}")
    public String deleteBrand(@PathVariable String id) {
        flashService.deleteBrandWithId(id);
        return "redirect:/flashes/manage";
    }


//    // CAMERA MODELS OPERATIONS

    @GetMapping("/flashes/manage/model/add")
    public String addFlash(Model model) {
        model.addAttribute("brands", flashService.getAllBrands());
        model.addAttribute("brandsCompatibility", cameraService.getAllBrands());
        if (!model.containsAttribute("noPictureSelected")) {
            model.addAttribute("noPictureSelected", false);
        }
        if (!model.containsAttribute("flashModel")) {
            model.addAttribute("flashModel", new FlashAddBindingModel());
        }
        return "flashes-add";
    }

    @PostMapping("/flashes/manage/model/add")
    public String addFlashConfirm(@Valid FlashAddBindingModel flashAdd,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || flashAdd.getPictures().isEmpty()) {
            redirectAttributes.addFlashAttribute("flashModel", flashAdd);
            redirectAttributes.addFlashAttribute("noPictureSelected", flashAdd.getPictures().isEmpty());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flashModel", bindingResult);
            return "redirect:/flashes/manage/model/add";
        }

        boolean result = flashService.addNewFlash(flashAdd);

        return "redirect:/flashes/manage";
    }

    @PostMapping("/flashes/manage/model/delete/{id}")
    public String deleteFlashModel(@PathVariable String id) {
        flashService.deleteModelById(id);
        return "redirect:/flashes/manage";
    }

    @GetMapping("/flashes/manage/edit/{id}")
    public String editFlash(@PathVariable String id, Model model) {
        model.addAttribute("brands", flashService.getAllBrands());
        if (!model.containsAttribute("flashModel")) {
            FlashModel flashById = flashService.getFlashById(id);
            FlashEditBindingModel editModel = modelMapper.map(flashById, FlashEditBindingModel.class);
            editModel
                    .setBrand(flashById.getBrand().getBrandName())
                    .setBrandCompatibility(flashById.getBrandCompatibility().getBrandName());
            model.addAttribute("flashModel", editModel)
                    .addAttribute("brandsCompatibility", cameraService.getAllBrands());
        }
        return "flashes-edit";
    }

    @PostMapping("/flashes/manage/edit/{id}")
    public String editFlashConfirm(@PathVariable String id,
                                    @Valid FlashEditBindingModel editModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flashModel", editModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flashModel", bindingResult);
            return "redirect:/flashes/manage/edit/" + id;
        }

        boolean result = flashService.editFlash(id, editModel);

        return "redirect:/flashes/manage";
    }

    // FLASH DETAILS VIEW

    @GetMapping("/flashes/details/{id}")
    public String showFlashDetails(@PathVariable String id, Model model) {
        model.addAttribute("flash", flashService.getFlashDetailsViewById(id));
        return "flashes-details";
    }

    @PostMapping("/flashes/details/{id}")
    public String addToCart(@PathVariable String id, @CurrentSecurityContext SecurityContext context) {
        cartService.addItemToCart(flashService.getFlashById(id), context, FlashModel.class);
        return "redirect:/flashes/details/" + id;
    }
}
