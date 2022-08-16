package softuni.photostore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import softuni.photostore.service.HomeService;

@Controller
public class HomeController {
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("models", homeService.getRandom3ProductsForHomepage());
        return "index";
    }

    @GetMapping(value = "/refresh", produces = "application/json")
    @ResponseBody
    public Object fetchProducts() {
        homeService.cleanRandom3ProductsCache();
        return homeService.getRandom3ProductsForHomepage();
    }

}
