package com.example.taskApp.purchases;

import com.example.taskApp.Product;
import com.example.taskApp.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PurchaseController {



    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/buyProduct/{id}")
    public String buyProduct(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String userEmail = principal.getAttribute("email");
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        Purchase purchase = new Purchase();
        purchase.setUserEmail(userEmail);
        purchase.setProduct(product);

        purchaseRepository.save(purchase);

        return "redirect:/products";
    }
}
