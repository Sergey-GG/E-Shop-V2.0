package vntu.fcsa.gonchar.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {



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
    public String signup(Model model) {
        model.addAttribute("title", "Sign up");
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
