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
import softuni.photostore.model.binding.*;
import softuni.photostore.model.entity.flashes.FlashModel;
import softuni.photostore.model.entity.tripods.TripodModel;
import softuni.photostore.model.service.TripodFilterModel;
import softuni.photostore.model.view.TripodViewModel;
import softuni.photostore.service.CartService;
import softuni.photostore.service.TripodService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TripodsController {
    private final TripodService tripodService;
    private final ModelMapper modelMapper;
    private final CartService cartService;

    public TripodsController(TripodService tripodService, ModelMapper modelMapper, CartService cartService) {
        this.tripodService = tripodService;
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    @GetMapping("/tripods")
    public String getAllTripods(Model model) {
        List<TripodViewModel> allTripods = tripodService.getAllTripodsOverviewModel();
        model.addAttribute("tripods", allTripods);
        model.addAttribute("tripodBrands", tripodService.getAllBrands());
        if (!model.containsAttribute("filter")) {
            model.addAttribute("filter", new TripodFilterModel().setBrand(""));
        }
        if (!model.containsAttribute("areTripodsAvailable")) {
            model.addAttribute("areTripodsAvailable", !allTripods.isEmpty());
        }
        return "tripods";
    }

    @PostMapping("/tripods")
    public String filterBags(TripodFilterModel filterModel, Model model) {
        List<TripodViewModel> allTripodsByFilterCriteria = tripodService.getAllTripodsByFilterCriteria(filterModel);
        model.addAttribute("tripods", allTripodsByFilterCriteria);
        model.addAttribute("tripodBrands", tripodService.getAllBrands());
        model.addAttribute("filter", filterModel);
        model.addAttribute("areTripodsAvailable", !allTripodsByFilterCriteria.isEmpty());
        return "tripods";
    }

    // TRIPOD BRANDS OPERATIONS

    @GetMapping("/tripods/manage")
    public String manage(Model model) {
        model.addAttribute("brands", tripodService.getAllBrands());
        model.addAttribute("tripods", tripodService.getAllTripodsForManagement());
        if (!model.containsAttribute("newBrand")) {
            model.addAttribute("newBrand", new TripodBrandAddBindingModel());
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
        return "tripods-manage";
    }

    @PostMapping("/tripods/manage/brand/add")
    public String addBrand(@Valid TripodBrandAddBindingModel brand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean brandExisting = tripodService.isBrandExisting(brand.getBrandName());

        if (brandExisting) {
            redirectAttributes.addFlashAttribute("brandExisting", brandExisting);
            return "redirect:/tripods/manage";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandNotMeetingRequirements", true);
            return "redirect:/tripods/manage";
        }
        boolean isRegistered = tripodService.registerNewBrand(brand);
        redirectAttributes.addFlashAttribute("brandAddedSuccessfully", isRegistered);
        return "redirect:/tripods/manage";
    }

    @PostMapping("tripods/manage/brand/delete/{id}")
    public String deleteBrand(@PathVariable String id) {
        tripodService.deleteBrandWithId(id);
        return "redirect:/tripods/manage";
    }

    @GetMapping("/tripods/manage/add")
    public String addTripod(Model model) {
        model.addAttribute("brands", tripodService.getAllBrands());
        if (!model.containsAttribute("noPictureSelected")) {
            model.addAttribute("noPictureSelected", false);
        }
        if (!model.containsAttribute("tripodModel")) {
            model.addAttribute("tripodModel", new TripodAddBindingModel());
        }
        return "tripods-add";
    }

    @PostMapping("/tripods/manage/add")
    public String addTripodConfirm(@Valid TripodAddBindingModel tripodAdd,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || tripodAdd.getPictures().isEmpty()) {
            redirectAttributes.addFlashAttribute("tripodModel", tripodAdd);
            redirectAttributes.addFlashAttribute("noPictureSelected", tripodAdd.getPictures().isEmpty());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.tripodModel", bindingResult);
            return "redirect:/tripods/manage/add";
        }

        boolean result = tripodService.addNewTripod(tripodAdd);

        return "redirect:/tripods/manage";
    }

    @PostMapping("/tripods/manage/delete/{id}")
    public String deleteTripodModel(@PathVariable String id) {
        tripodService.deleteModelById(id);
        return "redirect:/tripods/manage";
    }

    @GetMapping("/tripods/manage/edit/{id}")
    public String editTripod(@PathVariable String id, Model model) {
        model.addAttribute("brands", tripodService.getAllBrands());
        if (!model.containsAttribute("tripodModel")) {
            TripodModel tripodById = tripodService.getTripodById(id);
            TripodEditBindingModel editModel = modelMapper.map(tripodById, TripodEditBindingModel.class);
            editModel.setBrand(tripodById.getBrand().getBrandName());
            model.addAttribute("tripodModel", editModel);
        }
        return "tripods-edit";
    }

    @PostMapping("/tripods/manage/edit/{id}")
    public String editTripodConfirm(@PathVariable String id,
                                    @Valid TripodEditBindingModel editModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("tripodModel", editModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.tripodModel", bindingResult);
            return "redirect:/tripods/manage/edit/" + id;
        }

        tripodService.editTripod(id, editModel);

        return "redirect:/tripods/manage";
    }

    // BAGS DETAILS VIEW

    @GetMapping("/tripods/details/{id}")
    public String showBagDetails(@PathVariable String id, Model model) {
        model.addAttribute("tripod", tripodService.getTripodDetails(id));
        return "tripods-details";
    }

    @PostMapping("/tripods/details/{id}")
    public String addToCart(@PathVariable String id, @CurrentSecurityContext SecurityContext context) {
        cartService.addItemToCart(tripodService.getTripodById(id), context, TripodModel.class);
        return "redirect:/tripods/details/" + id;
    }
}
