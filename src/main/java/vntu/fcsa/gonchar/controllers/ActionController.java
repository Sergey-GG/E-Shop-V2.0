package vntu.fcsa.gonchar.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.dao.ProductDAO;
import vntu.fcsa.gonchar.entities.Product;

@Controller
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
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
//    @PreAuthorize("hasRole('ROLE_USER')")
    @Secured("ROLE_USER")
    public String buyProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productDAO.read(id));
        return "products/action/buy";
    }

    @PostMapping("/{id}/buy")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String buy(@PathVariable int id, @ModelAttribute("product") @Valid Product celledProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "products/action/buy";

        productDAO.updateToBuy(id, celledProduct);
        return printChek(celledProduct, id);
    }

    @GetMapping("/{id}/check")
    @PreAuthorize("hasRole('USER')")
    public String printChek(@Valid Product product, @PathVariable("id") int id) {
        return "products/action/printCheck";
    }
}

