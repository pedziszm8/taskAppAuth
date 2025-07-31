package com.example.taskApp.helpers;

public class StringHelper {

    public String toUpperCase(String name){
        String firstLetter = name.substring(0, 1);
        String lastLetters = name.substring(1);
        name = firstLetter.toUpperCase() + lastLetters;
        return name;
    }

}
