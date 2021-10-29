package vntu.fcsa.gonchar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import vntu.fcsa.gonchar.entities.User;
import vntu.fcsa.gonchar.repositories.UserRepository;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {

    UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home page");
        return "home";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Log in");
        return "login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("user")User user) {
        return "signup";
    }


    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("title", "Account");
        return "account";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        model.addAttribute("title", "Access denied!");
        return "accessDenied";
    }

}
