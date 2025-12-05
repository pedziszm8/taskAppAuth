package com.example.taskApp.category;

import com.example.taskApp.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController   //umozliwia zwrocenie api
@RequestMapping ("/api/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

/*    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }*/

    @GetMapping
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }




}
