package com.example.aloevibe.web;

import com.example.aloevibe.model.dto.CategoryDTO;
import com.example.aloevibe.service.CategoryService;
import com.example.aloevibe.service.ProductService;
import com.example.aloevibe.user.AloevibeUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model) {
        if (userDetails instanceof AloevibeUserDetails aloevibeUserDetails) {
            model.addAttribute("welcomeMessage", aloevibeUserDetails.getFullName());
        }

        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
//        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}
