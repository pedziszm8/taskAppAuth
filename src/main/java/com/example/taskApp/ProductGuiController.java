package com.example.taskApp;

import com.example.taskApp.category.Category;
import com.example.taskApp.category.CategoryRepository;
import com.example.taskApp.helpers.ProductPropertiesMapper;
import com.example.taskApp.helpers.ProductStringToMapConverter;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProductGuiController {

    @Autowired
    ProductHelper productHelper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductStringToMapConverter productStringToMapConverter;

    @Autowired
    private ProductPropertiesMapper productPropertiesMapper;

    @GetMapping("/productinfo")
    public String getproductinfo(Model model) {
        String productString = productHelper.returstring();            //utworzenie obiektu
        model.addAttribute("mojaZmienna", productString); //przekazanie obiektu do html
        return "productinfo";
    }


    @GetMapping("/products")
    public String getProducts(Model model) {
        // if (user != null) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);
        //  model.addAttribute("name", user.getAttribute("name"));
        //}
        System.out.println("MAP: " + productList);  //do sprawdzania
        for (Product product :
                productList) {
            System.out.println(product);
        }
        return "productView";
    }

    @GetMapping("/addNewProduct")                                           //wyswietlenie
    public String showAddProductForm(Model model) {
        model.addAttribute("newProduct", new Product());


        model.addAttribute("categories", categoryRepository.findAll());
        return "addNewProduct";
    }


////            poczatek
/*    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute Product product, Model model) {  //odniecienie pobranie atrybótow z frontend
        String error = "cena nie może by mniejsza rowna 0";
        if (product.price <= 0) {
            model.addAttribute("error", error);
            model.addAttribute("newProduct",product);

            return "addNewProduct";

            //    throw new RuntimeException(error);

        }

        Map<String, Object> converted = productStringToMapConverter.convert(product.rawPropertiesForMap);
        product.setProperties(converted);



        productRepository.save(product);  //do inerface
        return "redirect:/products";

    }*/

   /* @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute Product product, Model model) {
        String error = "cena nie może by mniejsza rowna 0";
        if (product.price <= 0) {
            model.addAttribute("error", error);
            model.addAttribute("newProduct", product);
            return "addNewProduct";
        }

        Map<String, Object> converted = productStringToMapConverter.convert(product.rawPropertiesForMap);
        product.setProperties(converted);

        // ✅ WYCZYŚĆ pole z formularza, żeby nie wracało do widoku
        product.setRawPropertiesForMap(null);

        productRepository.save(product);

        return "redirect:/products";
    }
*/

    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute Product product, Model model) {
        String error = "Cena nie może być mniejsza lub równa 0";
        if (product.price <= 0) {
            model.addAttribute("error", error);
            model.addAttribute("newProduct", product);
            return "addNewProduct";
        }

        // Jeśli pole properties jest puste, nie próbuj konwertować
        if (product.rawPropertiesForMap != null && !product.rawPropertiesForMap.trim().isEmpty()) {
            Map<String, Object> converted =   ProductStringToMapConverter.parseProperties(product.rawPropertiesForMap);   //stringToMap              zmiana z roductStringToMapConverter.convert(product.rawPropertiesForMap);    na      ProductStringToMapConverter.parseProperties(product.rawPropertiesForMap);
            product.setProperties(converted);
        } else {
            product.setProperties(null); // albo new HashMap<>(), jeśli wolisz pustą mapę
        }


        // Wyczyść pole textarea
        product.setRawPropertiesForMap(null);

        productRepository.save(product);
        return "redirect:/products";
    }


    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        System.out.println("Id suswanego produktu:" + id);
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/editProduct/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.orElseThrow(() -> new RuntimeException("Invalid id"));
        model.addAttribute("product", product);
        String rawPropertiesForMap = productPropertiesMapper.mapToString(product.getProperties());   //MapToString (userString)  //wymieniono z 2
        product.setRawPropertiesForMap(
                rawPropertiesForMap
        );
        return "editProduct";
    }

    //ctr atl m
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product) {

        if (product.rawPropertiesForMap != null && !product.rawPropertiesForMap.trim().isEmpty()) {
            Map<String, Object> converted = ProductStringToMapConverter.parseProperties(product.rawPropertiesForMap);    //stringToMap (userStringToMap)
            product.setProperties(converted);
        } else {
            product.setProperties(null); // albo new HashMap<>(), jeśli wolisz pustą mapę
        }
        productRepository.save(product);


        return "redirect:/products";
    }
/*    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, Model model) {

        // walidacja ceny
        if (product.price <= 0) {
            model.addAttribute("error", "Cena nie może być <= 0");
            model.addAttribute("product", product);
            return "editProduct";
        }

        // konwersja jeśli user dał nowe properties
        if (product.rawPropertiesForMap != null && !product.rawPropertiesForMap.isBlank()) {
            Map<String, Object> converted = productStringToMapConverter.convert(product.rawPropertiesForMap);
            product.setProperties(converted);
        }

        // ✅ ZAWSZE czyścimy raw pole
        product.setRawPropertiesForMap(null);

        productRepository.save(product);

        return "redirect:/products";
    }

*/


    /*  @PostMapping("/addNewTask")
    public String addNewTask(@ModelAttribute Task task,
                             @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                task.setImage(imageFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        taskRepository.save(task);
        return "redirect:/taskList";
    }*/


}
