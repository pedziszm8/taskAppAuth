package com.example.taskApp.statistics;

import jakarta.persistence.*;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String email;

    @Column
    String name;

    @Column
    String last_name;

    @Column
    Long amountOfLogging=0L;

    public void incrementAmountOFLog(){
        amountOfLogging+=1;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public Long getAmountOfLogging() {
        return amountOfLogging;
    }

    public void setAmountOfLogging(Long amountOfLogging) {
        this.amountOfLogging = amountOfLogging;
    }



}
