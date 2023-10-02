package org.example.service;

import org.example.entity.Task;

import java.util.List;


public interface TaskService {
    List<Task> getAllTasks();
    List<Task> findByUserId(Long userId);
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
}