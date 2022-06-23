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
import softuni.photostore.model.binding.BagAddBindingModel;
import softuni.photostore.model.binding.BagBrandAddBindingModel;
import softuni.photostore.model.binding.BagEditBindingModel;
import softuni.photostore.model.entity.bags.BagModel;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.service.BagFilterModel;
import softuni.photostore.model.view.BagViewModel;
import softuni.photostore.service.BagsService;
import softuni.photostore.service.CartService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BagsController {
    private final BagsService bagsService;
    private final ModelMapper modelMapper;
    private final CartService cartService;

    public BagsController(BagsService bagsService, ModelMapper modelMapper, CartService cartService) {
        this.bagsService = bagsService;
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    @GetMapping("/bags/{type}")
    public String getBagsByType(@PathVariable String type, Model model) {
        List<BagViewModel> allBagsByType = bagsService.getAllBagsByTypeForOverview(type);
        model.addAttribute("bags", allBagsByType);
        model.addAttribute("bagBrands", bagsService.getAllBrands());
        if (!model.containsAttribute("filter")) {
            model.addAttribute("filter", new BagFilterModel().setBrand(""));
        }
        if (!model.containsAttribute("areBagsAvailable")) {
            model.addAttribute("areBagsAvailable", !allBagsByType.isEmpty());
        }
        return "bags";
    }

    @PostMapping("/bags/{type}")
    public String filterBags(@PathVariable String type, BagFilterModel filterModel, Model model) {
        List<BagViewModel> allBagsByFilterCriteria = bagsService.getAllBagsByFilterCriteria(filterModel, type);
        model.addAttribute("bags", allBagsByFilterCriteria);
        model.addAttribute("bagBrands", bagsService.getAllBrands());
        model.addAttribute("filter", filterModel);
        model.addAttribute("areBagsAvailable", !allBagsByFilterCriteria.isEmpty());
        return "bags";
    }

    // BAGS BRANDS OPERATIONS

    @GetMapping("/bags/manage")
    public String manage(Model model) {
        model.addAttribute("brands", bagsService.getAllBrands());
        model.addAttribute("bags", bagsService.getAllBagsForManagement());
        if (!model.containsAttribute("newBrand")) {
            model.addAttribute("newBrand", new BagBrandAddBindingModel());
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
        return "bags-manage";
    }

    @PostMapping("/bags/manage/brand/add")
    public String addBrand(@Valid BagBrandAddBindingModel brand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean brandExisting = bagsService.isBrandExisting(brand.getBrandName());

        if (brandExisting) {
            redirectAttributes.addFlashAttribute("brandExisting", brandExisting);
            return "redirect:/bags/manage";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandNotMeetingRequirements", true);
            return "redirect:/bags/manage";
        }
        boolean isRegistered = bagsService.registerNewBrand(brand);
        redirectAttributes.addFlashAttribute("brandAddedSuccessfully", isRegistered);
        return "redirect:/bags/manage";
    }

    @PostMapping("bags/manage/brand/delete/{id}")
    public String deleteBrand(@PathVariable String id) {
        bagsService.deleteBrandWithId(id);
        return "redirect:/bags/manage";
    }


    // BAGS MODELS OPERATIONS

    @GetMapping("/bags/manage/model/add")
    public String addBag(Model model) {
        model.addAttribute("brands", bagsService.getAllBrands());
        if (!model.containsAttribute("noPictureSelected")) {
            model.addAttribute("noPictureSelected", false);
        }
        if (!model.containsAttribute("bagModel")) {
            model.addAttribute("bagModel", new BagAddBindingModel());
        }
        return "bags-add";
    }

    @PostMapping("/bags/manage/model/add")
    public String addBagConfirm(@Valid BagAddBindingModel bagAdd,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || bagAdd.getPictures().isEmpty()) {
            redirectAttributes.addFlashAttribute("bagModel", bagAdd);
            redirectAttributes.addFlashAttribute("noPictureSelected", bagAdd.getPictures().isEmpty());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bagModel", bindingResult);
            return "redirect:/bags/manage/model/add";
        }

        boolean result = bagsService.addNewBag(bagAdd);

        return "redirect:/bags/manage";
    }

    @PostMapping("/bags/manage/model/delete/{id}")
    public String deleteBagModel(@PathVariable String id) {
        bagsService.deleteModelById(id);
        return "redirect:/bags/manage";
    }

    @GetMapping("/bags/manage/edit/{id}")
    public String editBag(@PathVariable String id, Model model) {
        model.addAttribute("brands", bagsService.getAllBrands());
        if (!model.containsAttribute("bagModel")) {
            BagModel bagById = bagsService.getBagById(id);
            BagEditBindingModel editModel = modelMapper.map(bagById, BagEditBindingModel.class);
            editModel.setBrand(bagById.getBrand().getBrandName());
            model.addAttribute("bagModel", editModel);
        }
        return "bags-edit";
    }

    @PostMapping("/bags/manage/edit/{id}")
    public String editLensConfirm(@PathVariable String id,
                                    @Valid BagEditBindingModel editModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bagModel", editModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bagModel", bindingResult);
            return "redirect:/bags/manage/edit/" + id;
        }

        bagsService.editBag(id, editModel);

        return "redirect:/bags/manage";
    }

    // BAGS DETAILS VIEW

    @GetMapping("/bags/details/{id}")
    public String showBagDetails(@PathVariable String id, Model model) {
        model.addAttribute("bag", bagsService.getBagDetailsById(id));
        return "bags-details";
    }

    @PostMapping("/bags/details/{id}")
    public String addToCart(@PathVariable String id, @CurrentSecurityContext SecurityContext context) {
        cartService.addItemToCart(bagsService.getBagById(id), context, BagModel.class);
        return "redirect:/bags/details/" + id;
    }
}
