package vntu.fcsa.gonchar.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.entities.User;
import vntu.fcsa.gonchar.repositories.UserRepository;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class AdminAPIController {
    UserRepository userRepository;
    @Autowired
    public AdminAPIController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/showUsers")
    public String showUsers(Model model) {
        return "admin/editUsers/showUsers";
    }


    @GetMapping("/users/show/{id}")
    public String showUserToEdit(@PathVariable int id, Model model) {
        return "admin/editUsers/selectUser";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        return "admin/editUsers/editUser";
    }



    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/editUsers/editUser";

        return "redirect:/admin/showUsers";
    }
    @PostMapping("/products/{id}/delete")
    public String deleteUser(@PathVariable("id") int id){

        return "redirect:/admin/showUsers";
    }
}
