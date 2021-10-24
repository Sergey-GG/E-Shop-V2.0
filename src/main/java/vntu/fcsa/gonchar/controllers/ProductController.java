package vntu.fcsa.gonchar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vntu.fcsa.gonchar.dao.ProductDAO;
import vntu.fcsa.gonchar.repositories.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductDAO productDAO;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductDAO productDAO, ProductRepository productRepository) {
        this.productDAO = productDAO;
        this.productRepository = productRepository;
    }

    @GetMapping("/milks")
    public String milks(Model model) {
        model.addAttribute("milkProducts", productDAO.getProducts("milks"));
        return "products/milkProducts";
    }

    @GetMapping("/meats")
    public String meats(Model model) {
        model.addAttribute("meatProducts", productDAO.getProducts("meats"));

        return "products/meatProducts";
    }

    @GetMapping("/drinks")
    public String drinks(Model model) {
        model.addAttribute("drinks", productDAO.getProducts("drinks"));

        return "products/drinks";
    }
}
