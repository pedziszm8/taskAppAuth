package com.example.taskApp;

import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductGuiController {

    @Autowired
    ProductHelper productHelper;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping ("/productinfo")
    public String getproductinfo(Model model) {
        String productString = productHelper.returstring();            //utworzenie obiektu
        model.addAttribute("mojaZmienna",productString); //przekazanie obiektu do html
        return "productinfo";
    }



    @GetMapping("/products")
    public String getProducts(Model model) {
        // if (user != null) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);
        //  model.addAttribute("name", user.getAttribute("name"));
        //}
        return "producuctinfo";
    }
}
