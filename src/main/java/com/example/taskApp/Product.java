package com.example.taskApp;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "Nazwa")  //nazwa kolumny mazna wykasowac pozniej
    String product_name;
    @Column(name = "cena")
    Double price;
    @Column
    LocalDate data;
    @Column
    File picture;
    //@Column
   // Map<String, Object> properties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

//    public Map<String, Object> getProperties() {
//        return properties;
//    }
//
//    public void setProperties(Map<String, Object> properties) {
//        this.properties = properties;
//    }
}
