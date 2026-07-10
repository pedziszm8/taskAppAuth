package com.example.taskApp;

import com.example.taskApp.category.Category;
import com.example.taskApp.category.CategoryRepository;
import com.example.taskApp.helpers.ProductPropertiesMapper;
import com.example.taskApp.helpers.ProductStringToMapConverter;
import com.example.taskApp.statistics.Role;
import com.example.taskApp.statistics.Statistics;
import com.example.taskApp.statistics.StatisticsRepository;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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

    @Autowired
    private StatisticsRepository statisticsRepository;

    @GetMapping("/productinfo")
    public String getproductinfo(Model model) {
        String productString = productHelper.returstring();            //utworzenie obiektu
        model.addAttribute("mojaZmienna", productString); //przekazanie obiektu do html
        return "productinfo";
    }


    @GetMapping("/products")
    public String getProducts(@AuthenticationPrincipal OAuth2User user, Model model) {
        if (user != null) {
            Optional<Statistics> optionalUserStatistic = statisticsRepository.findByEmail(user.getAttribute("email"));
            if(optionalUserStatistic.isPresent()){
                Role role = optionalUserStatistic.get().getRole();
                if (role.equals(Role.ADMIN)){
                    model.addAttribute("role","ADMIN");      //odwolanie - przekazanie zmiennej do frontend

                }
            }
            List<Product> productList = productRepository.findAll();
            model.addAttribute("products", productList);
            //  model.addAttribute("name", user.getAttribute("name"));
        }
        /*System.out.println("MAP: " + productList);  //do sprawdzania
        for (Product product :
                productList) {
            System.out.println(product);
        }*/
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
    public String addNewProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile, Model model) {         /// @RequestParam("imageFile") MultipartFile imageFile
        String error = "Cena nie może być mniejsza lub równa 0";
        if (product.price <= 0) {
            model.addAttribute("error", error);
            model.addAttribute("newProduct", product);
            return "addNewProduct";
        }

        // Jeśli pole properties jest puste, nie próbuj konwertować
        if (product.rawPropertiesForMap != null && !product.rawPropertiesForMap.trim().isEmpty()) {
            Map<String, Object> converted = ProductStringToMapConverter.parseProperties(product.rawPropertiesForMap);   //stringToMap              zmiana z roductStringToMapConverter.convert(product.rawPropertiesForMap);    na      ProductStringToMapConverter.parseProperties(product.rawPropertiesForMap);
            product.setProperties(converted);
        } else {
            product.setProperties(null); // albo new HashMap<>(), jeśli wolisz pustą mapę
        }


        // Wyczyść pole textarea
        product.setRawPropertiesForMap(null);

        try {
            if (!imageFile.isEmpty()) {
                product.setPicture(imageFile.getBytes());
                product.setPictureContentType(imageFile.getContentType());
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas odczytu obrazu", e);
        }                                    ///


        product.setData(LocalDate.now());///

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
    public String updateProduct(@ModelAttribute Product product,  @RequestParam("imageFile") MultipartFile imageFile) {          /// @RequestParam("imageFile") MultipartFile imageFile

        if (product.rawPropertiesForMap != null && !product.rawPropertiesForMap.trim().isEmpty()) {
            Map<String, Object> converted = ProductStringToMapConverter.parseProperties(product.rawPropertiesForMap);    //stringToMap (userStringToMap)
            product.setProperties(converted);
        } else {
            product.setProperties(null); // albo new HashMap<>(), jeśli wolisz pustą mapę
        }


        try {
            if (!imageFile.isEmpty()) {
                product.setPicture(imageFile.getBytes());
                product.setPictureContentType(imageFile.getContentType());
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas odczytu obrazu", e);
        }  ///

        productRepository.save(product);


        return "redirect:/products";
    }

    @GetMapping("/productPicture/{id}")
    @ResponseBody
    public byte[] getProductPicture(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono produktu"));

        return product.getPicture();
    }


    @GetMapping("/productImage/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow();

        if (product.getPicture() == null) {
            return ResponseEntity.notFound().build();
        }

        String contentType = product.getPictureContentType();

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(product.getPicture());
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
