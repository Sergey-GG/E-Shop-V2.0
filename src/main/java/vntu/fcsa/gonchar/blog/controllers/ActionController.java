package vntu.fcsa.gonchar.blog.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.blog.dao.ProductDAO;
import vntu.fcsa.gonchar.blog.models.Product;

@Controller
@RequestMapping("/products")
public class ActionController {
    private final ProductDAO productDAO;

    @Autowired
    public ActionController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productDAO.read(id));
        return "products/action/showProduct";
    }

    @GetMapping("/{id}/buy")
    public String buyProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productDAO.read(id));
        return "products/action/buy";
    }

    @PostMapping("/{id}/buy")
    public String buy(@PathVariable int id, @ModelAttribute("product") @Valid Product celledProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "products/action/buy";

        productDAO.updateToBuy(id, celledProduct);
        return printChek(celledProduct, id);
    }

    @GetMapping("/{id}/check")
    public String printChek(@Valid Product product, @PathVariable("id") int id) {
        return "products/action/printCheck";
    }
}

