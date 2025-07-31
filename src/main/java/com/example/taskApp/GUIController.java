package com.example.taskApp;

import com.example.taskApp.helpers.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class GUIController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/displayvariable")
    public String displayVariable(Model model) {
        String wyswietl = "definicja";

        model.addAttribute("mojaZmienna", wyswietl);
        return "displayVariable";
    }

    @GetMapping("/taskList")
    public String showTaskList(@AuthenticationPrincipal OAuth2User user, Model model) {
        if (user != null) {
            List<Task> taskList = taskRepository.findAll();
            model.addAttribute("tasks", taskList);
            model.addAttribute("name", user.getAttribute("name"));
        }

        return "taskList";
    }

    @GetMapping("/")
    public String showHomePage(@AuthenticationPrincipal OAuth2User user, Model model) {
        if (user != null) {
            String givenName = user.getAttribute("given_name");
/*            String firstLetter = givenName.substring(0, 1);
            String lastLetters = givenName.substring(1);
            givenName = firstLetter.toUpperCase() + lastLetters;*/
            StringHelper stringHelper=new StringHelper();
            model.addAttribute("name", stringHelper.toUpperCase(givenName));
            // model.addAttribute("ImageUrl", user.getAttribute("ImageUrl"));
            model.addAttribute("picture", user.getAttribute("picture"));

        }
        return "home";
    }

    @GetMapping("/public")
    public String showPublic(Model model) {
        return "public";
    }


/*    @GetMapping("/taskImage/{id}")
    @ResponseBody
    public byte[] getTaskImage(@PathVariable("id") Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null && task.getImage() != null) {
            return task.getImage();
        }
        return new byte[0]; // lub obraz placeholder
    }*/


    @GetMapping("/addNewTask")
    public String showAddTaskForm(Model model) {
        model.addAttribute("newTask", new Task());
        return "addNewTask";
    }

    @PostMapping("/addNewTask")
    public String addNewTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/taskList";

    }

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


    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") long id) {
        System.out.println("Id suswanego taska:" + id);
        taskRepository.deleteById(id);
        return "redirect:/taskList";
    }

    @PostMapping("/editTask/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Optional<Task> byId = taskRepository.findById(id);
        Task task = byId.orElseThrow(() -> new RuntimeException("Invalid id"));
        model.addAttribute("task", task);
        return "editTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/taskList";
    }
}
