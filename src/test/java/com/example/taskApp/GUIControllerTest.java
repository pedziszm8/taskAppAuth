package com.example.taskApp;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(GUIController.class)
class GUIControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void displayVariable() {
    }

    @Test
    void showTaskList() {
    }

    @Test
    void showHomePage() {
    }

    @Test
    void showPublic() {
    }

    @Test
    void showAddTaskForm() throws Exception {
        mockMvc.perform(get("/addNewTask"))
                .andExpect(status().isOk())
                .andExpect(view().name("addNewTask"))
                .andExpect(model().attributeExists("newTask"))
                .andExpect(model().attribute("newTask", org.hamcrest.Matchers.instanceOf(Task.class)));

    }

    @Test
    void addNewTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void showEditForm() {
    }

    @Test
    void updateTask() {
    }
}