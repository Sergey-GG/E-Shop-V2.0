package vntu.fcsa.gonchar.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.dao.ProductDAO;
import vntu.fcsa.gonchar.models.Product;
import vntu.fcsa.gonchar.repositories.ProductRepository;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductDAO productDAO;
    private final ProductRepository productRepository;

    @Autowired
    public AdminController(ProductDAO productDAO, ProductRepository productRepository) {
        this.productDAO = productDAO;
        this.productRepository = productRepository;
    }

    @GetMapping("/showAllProducts")
    public String showAll(Model model) {
        model.addAttribute("products", productDAO.getProducts("all"));
        return "admin/editProducts/showProducts/allProducts";
    }

    @GetMapping("/showMilks")
    public String showMilks(Model model) {
        model.addAttribute("milkProducts", productDAO.getProducts("milks"));
        return "admin/editProducts/showProducts/milkProducts";
    }

    @GetMapping("/showMeats")
    public String showMeats(Model model) {
        model.addAttribute("meatProducts", productDAO.getProducts("meats"));
        return "admin/editProducts/showProducts/meatProducts";
    }

    @GetMapping("/showDrinks")
    public String showDrinks(Model model) {
        model.addAttribute("drinks", productDAO.getProducts("drinks"));
        return "admin/editProducts/showProducts/drinks";
    }

    @GetMapping("/products/new")
    public String createNewProduct(@ModelAttribute("product") Product product) {
        return "admin/editProducts/newProduct";
    }

    @GetMapping("/products/show/{id}")
    public String showProductToEdit(@PathVariable int id, Model model) {
        model.addAttribute("product", productDAO.read(id));
        return "admin/editProducts/showProduct";
    }

    @GetMapping("/products/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productDAO.read(id));
        return "admin/editProducts/editProduct";
    }

    @PostMapping("/newProduct")
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/editProducts/newProduct";

        productDAO.create(product);
        return "redirect:/admin/showAllProducts";
    }

    @PostMapping("/products/{id}/edit")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/editProducts/editProduct";

        productDAO.update(id, product);
        return "redirect:/admin/showAllProducts";
    }
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable("id") int id){
        productDAO.delete(id);
        return "redirect:/admin/showAllProducts";
    }

}
