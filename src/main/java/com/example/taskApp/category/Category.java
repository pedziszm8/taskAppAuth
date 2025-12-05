package com.example.taskApp.category;

import com.example.taskApp.Product;
import jakarta.persistence.*;
import org.hibernate.mapping.List;

import java.util.Set;

@Entity // spowoduje utworzenie w bazie danych
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "kategoria") //opcjonalnie jesli che innej nazwy
    private String category;

    private String description;


    @OneToMany(mappedBy = "category")
    Set<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
