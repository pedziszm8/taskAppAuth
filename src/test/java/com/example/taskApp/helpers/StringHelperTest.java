package com.example.taskApp.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {

    @Test
    void toUpperCase() {
        String word = "word";
        StringHelper stringHelper= new StringHelper();
        Assertions.assertTrue(word.toUpperCase()=="Word");
    }
}