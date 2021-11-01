package vntu.fcsa.gonchar.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.dao.ProductDAO;
import vntu.fcsa.gonchar.entities.Product;
import vntu.fcsa.gonchar.repositories.ProductRepository;

import javax.annotation.security.RolesAllowed;


@Controller
@RequestMapping("/moder")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
//@Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_MODERATOR", "ROLE_ADMIN"})
public class ModeratorAPIController {
    private final ProductDAO productDAO;
    private final ProductRepository productRepository;

    @Autowired
    public ModeratorAPIController(ProductDAO productDAO, ProductRepository productRepository) {
        this.productDAO = productDAO;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public String showAll(Model model) {
        model.addAttribute("products", productDAO.getProducts("all"));
        return "moderator/editProducts/showProducts/allProducts";
    }

    @GetMapping("/products/milks")
    public String showMilks(Model model) {
        model.addAttribute("milkProducts", productDAO.getProducts("milks"));
        return "moderator/editProducts/showProducts/milkProducts";
    }

    @GetMapping("/products/meats")
    public String showMeats(Model model) {
        model.addAttribute("meatProducts", productDAO.getProducts("meats"));
        return "moderator/editProducts/showProducts/meatProducts";
    }

    @GetMapping("/products/drinks")
    public String showDrinks(Model model) {
        model.addAttribute("drinks", productDAO.getProducts("drinks"));
        return "moderator/editProducts/showProducts/drinks";
    }

    @GetMapping("/products/new")
    public String createNewProduct(@ModelAttribute("product") Product product) {
        return "moderator/editProducts/newProduct";
    }

    @GetMapping("/product/{id}")
    public String showProductToEdit(@PathVariable int id, Model model) {
        model.addAttribute("product", productDAO.read(id));
        return "moderator/editProducts/showProduct";
    }

    @GetMapping("/product/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productDAO.read(id));
        return "moderator/editProducts/editProduct";
    }

    @PostMapping("/products/newProduct")
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "moderator/editProducts/newProduct";

        productDAO.create(product);
        return "redirect:/moder/products";
    }

    @PostMapping("/product/{id}/edit")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "moderator/editProducts/editProduct";

        productDAO.update(id, product);
        return "redirect:/moder/products";
    }

    @PostMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") int id) {
        productDAO.delete(id);
        return "redirect:/moder/products";
    }

}
