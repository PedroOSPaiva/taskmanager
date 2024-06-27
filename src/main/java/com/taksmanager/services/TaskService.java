package com.taksmanager.services;

import com.taksmanager.models.Task;
import com.taksmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public Optional<Task> getTask(Long id){
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByUserId(Long userId){
        return taskRepository.findByUserId(userId);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
