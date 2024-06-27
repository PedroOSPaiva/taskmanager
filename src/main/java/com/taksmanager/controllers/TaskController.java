package com.taksmanager.controllers;

import com.taksmanager.models.Task;
import com.taksmanager.services.TaskService;
import com.taksmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task, Authentication authentication) {
        String username = authentication.getName();
        task.setUser(userService.findByUsername(username).orElseThrow());
        return taskService.saveTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.findByUsername(username).orElseThrow().getId();
        return taskService.getTasksByUserId(userId);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails, Authentication authentication) {
        Task task = taskService.getTask(id).orElseThrow();
        if (!task.getUser().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized");
        }
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        return taskService.saveTask(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        Task task = taskService.getTask(id).orElseThrow();
        if (!task.getUser().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized");
        }
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
