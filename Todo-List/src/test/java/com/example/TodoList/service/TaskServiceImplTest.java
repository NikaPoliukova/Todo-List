package com.example.TodoList.service;

import com.example.TodoList.entity.Task;
import com.example.TodoList.hendler.ResourceNotFoundException;
import com.example.TodoList.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        verify(taskRepository, times(1)).findAll();
        assertSame(tasks, result);
    }

    @Test
    public void testFindByUserId() {
        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findByUserId(userId)).thenReturn(tasks);

        List<Task> result = taskService.findByUserId(userId);

        verify(taskRepository, times(1)).findByUserId(userId);
        assertSame(tasks, result);
    }

    @Test
    public void testGetTaskById() {
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        assertSame(task, result);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(task);

        verify(taskRepository, times(1)).save(task);
        assertSame(task, result);
    }

    @Test
    public void testUpdateTask_SuccessfulUpdate() {
        Task originalTask = new Task();
        originalTask.setId(1L);
        originalTask.setTitle("Задача 1");
        originalTask.setDescription("Описание задачи 1");

        // Создаем обновленную задачу
        Task updatedTask = new Task();
        updatedTask.setId(1L); // Устанавливаем ID
        updatedTask.setTitle("Новый заголовок");
        updatedTask.setDescription("Новое описание");

        // Устанавливаем поведение для findById и save
        when(taskRepository.findById(1L)).thenReturn(Optional.of(originalTask));
        when(taskRepository.save(originalTask)).thenReturn(updatedTask);

        // Вызываем метод updateTask
        Task result = taskService.updateTask(1L, updatedTask);

        // Проверяем, что заголовок и описание были обновлены
        assertEquals("Новый заголовок", result.getTitle());
        assertEquals("Новое описание", result.getDescription());

        // Проверяем, что метод save был вызван один раз с объектом originalTask
        verify(taskRepository, times(1)).save(originalTask);
    }

    @Test
    public void testUpdateTask_TaskNotFound() {
        // Создаем обновленную задачу
        Task updatedTask = new Task();
        updatedTask.setTitle("Новый заголовок");
        updatedTask.setDescription("Новое описание");

        // Устанавливаем поведение для findById (возвращаем пустой Optional)
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Вызываем метод updateTask и ожидаем ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.updateTask(1L, updatedTask);
        });

        // Проверяем, что метод save не был вызван
        verify(taskRepository, never()).save(any());
    }


    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        taskService.deleteTask(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
