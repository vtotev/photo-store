package softuni.photostore.web;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.photostore.model.binding.CameraAddBindingModel;
import softuni.photostore.model.binding.CameraBrandAddBindingModel;
import softuni.photostore.model.entity.BaseModel;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.model.entity.cameras.CameraModel;
import softuni.photostore.model.entity.enums.CameraTypeEnum;
import softuni.photostore.model.service.CameraFilterModel;
import softuni.photostore.model.view.CameraViewModel;
import softuni.photostore.service.CameraService;
import softuni.photostore.service.CartService;
import softuni.photostore.service.UsersService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CameraController {
    private final CameraService cameraService;
    private final ModelMapper modelMapper;
    private final UsersService usersService;
    private final CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(CameraController.class);

    public CameraController(CameraService cameraService, ModelMapper modelMapper, UsersService usersService, CartService cartService) {
        this.cameraService = cameraService;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
        this.cartService = cartService;
    }

    @GetMapping("/cameras/{type}")
    public String getCamerasByBrand(@PathVariable String type, Model model) {
        List<CameraModel> allCamerasByType = cameraService.getAllCamerasByType(type);
        model.addAttribute("cameras", allCamerasByType);
        model.addAttribute("camType", CameraTypeEnum.valueOf(type.toUpperCase()).getTitle());
        if (!model.containsAttribute("filter")) {
            model.addAttribute("filter", new CameraFilterModel()
                    .setBrand("")
                    .setSensorSize(""));
        }
        if (!model.containsAttribute("areCamerasAvailable")) {
            model.addAttribute("areCamerasAvailable", !allCamerasByType.isEmpty());
        }
        return "cameras";
    }

    @PostMapping("/cameras/{type}")
    public String filterCameras(@PathVariable String type, CameraFilterModel filterModel, Model model) {
        List<CameraViewModel> allCamerasByFilterCriteria = cameraService.getAllCamerasByFilterCriteria(filterModel, type)
                .stream().map(c -> modelMapper.map(c, CameraViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("cameras", allCamerasByFilterCriteria);
        model.addAttribute("camType", CameraTypeEnum.valueOf(type.toUpperCase()).getTitle());
        model.addAttribute("filter", filterModel);
        model.addAttribute("areCamerasAvailable", !allCamerasByFilterCriteria.isEmpty());
        return "cameras";
    }

    // CAMERA BRANDS OPERATIONS

    @GetMapping("/cameras/manage")
    public String manage(Model model) {
        model.addAttribute("brands", cameraService.getAllBrands());
        model.addAttribute("cameras", cameraService.getAllCamerasForManagement());
        if (!model.containsAttribute("newBrand")) {
            model.addAttribute("newBrand", new CameraBrandAddBindingModel());
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
        return "cameras-manage";
    }

    @PostMapping("/cameras/manage/brand/add")
    public String addBrand(@Valid CameraBrandAddBindingModel brand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean brandExisting = cameraService.isBrandExisting(brand.getBrandName());

        if (brandExisting) {
            redirectAttributes.addFlashAttribute("brandExisting", brandExisting);
            return "redirect:/cameras/manage";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandNotMeetingRequirements", true);
            return "redirect:/cameras/manage";
        }
        boolean isRegistered = cameraService.registerNewBrand(brand);
        redirectAttributes.addFlashAttribute("brandAddedSuccessfully", isRegistered);
        return "redirect:/cameras/manage";
    }

    @PostMapping("cameras/manage/brand/delete/{id}")
    public String deleteBrand(@PathVariable String id) {
        cameraService.deleteBrandWithId(id);
        return "redirect:/cameras/manage";
    }


    // CAMERA MODELS OPERATIONS

    @GetMapping("/cameras/manage/model/add")
    public String addCamera(Model model) {
        model.addAttribute("brands", cameraService.getAllBrands());
        if (!model.containsAttribute("noPictureSelected")) {
            model.addAttribute("noPictureSelected", false);
        }
        if (!model.containsAttribute("cameraModel")) {
            model.addAttribute("cameraModel", new CameraAddBindingModel());
        }
        return "camera-add";
    }

    @PostMapping("/cameras/manage/model/add")
    public String addCameraConfirm(@Valid CameraAddBindingModel cameraAddBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || cameraAddBindingModel.getPicture().isEmpty()) {
            redirectAttributes.addFlashAttribute("cameraModel", cameraAddBindingModel);
            redirectAttributes.addFlashAttribute("noPictureSelected", cameraAddBindingModel.getPicture().isEmpty());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cameraModel", bindingResult);
            return "redirect:/cameras/manage/model/add";
        }

        cameraService.addNewCamera(cameraAddBindingModel);

        return "redirect:/cameras/manage";
    }

    @PostMapping("/cameras/manage/model/delete/{id}")
    public String deleteCameraModel(@PathVariable String id) {
        cameraService.deleteModelById(id);
        return "redirect:/cameras/manage";
    }

    @GetMapping("/cameras/manage/edit/{id}")
    public String editCamera(@PathVariable String id, Model model) {
        model.addAttribute("brands", cameraService.getAllBrands());
        if (!model.containsAttribute("cameraModel")) {
            CameraModel cameraById = cameraService.getCameraById(id);
            CameraAddBindingModel editModel = modelMapper.map(cameraById, CameraAddBindingModel.class);
            editModel.setBrand(cameraById.getBrand().getBrandName());
            model.addAttribute("cameraModel", cameraService.getCameraById(id));
        }
        return "camera-edit";
    }

    @PostMapping("/cameras/manage/edit/{id}")
    public String editCameraConfirm(@PathVariable String id,
                                    @Valid CameraAddBindingModel cameraAddBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("cameraModel", cameraAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cameraModel", bindingResult);
            return "redirect:/cameras/manage/edit/" + id;
        }

        cameraService.editCamera(id, cameraAddBindingModel);

        return "redirect:/cameras/manage";
    }

    // CAMERA DETAILS VIEW

    @GetMapping("/cameras/details/{id}")
    public String showCameraDetails(@PathVariable String id, Model model) {
        model.addAttribute("camera", cameraService.getCameraDetailsById(id));
//        model.addAttribute("remoteip", ((WebAuthenticationDetails) context.getAuthentication().getDetails()).getRemoteAddress());
        return "camera-details";
    }

    @PostMapping("/cameras/details/{id}")
    public String addToCart(@PathVariable String id, @CurrentSecurityContext SecurityContext context) {
        cartService.addItemToCart(cameraService.getCameraById(id), context, CameraModel.class);
//        User user = null;
//        String remoteIP = "";
//        var product = cameraService.getCameraById(id);
//        Authentication authentication = context.getAuthentication();
//        AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
//        if (!authenticationTrustResolver.isAnonymous(authentication)) {
//            user = usersService.getUserByUsername(authentication.getName());
//        }
//        if (context.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
//            WebAuthenticationDetails authDetails = (WebAuthenticationDetails) context.getAuthentication().getDetails();
//            remoteIP = authDetails.getRemoteAddress();
//        }
//        cartService.addItemToCart(user != null ? user.getId() : context.getAuthentication().getName(), remoteIP, product, 1, product.getClass().getSimpleName());
        return "redirect:/cameras/details/" + id;
    }
}