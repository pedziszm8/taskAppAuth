package com.example.taskApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        Task saved = taskRepository.save(task);
        return saved;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        return taskList;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));


    }

    @DeleteMapping
    public void deleteTasks() {
        taskRepository.deleteAll();

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task task1 = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task1.setDescription(task.getDescription());
        task1.setStatus(task.status);
        return taskRepository.save(task1);
    }



}
