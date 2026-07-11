package com.example.taskApp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryGuiController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String showCategories(Model model) {

        model.addAttribute("categories", categoryRepository.findAll());

        return "categoryView";
    }

    @GetMapping("/addCategory")
    public String addCategoryForm(Model model){

        model.addAttribute("category", new Category());

        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category){

        categoryRepository.save(category);

        return "redirect:/categories";
    }

}