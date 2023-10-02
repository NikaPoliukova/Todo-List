package com.example.TodoList.controller;

import com.example.TodoList.entity.Task;
import com.example.TodoList.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("title1");
        task1.setDescription("супер title 1");
        tasks.add(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("title 2");
        task2.setDescription("супер title 2");
        tasks.add(task2);

        when(taskService.getAllTasks()).thenReturn(tasks);
        List<Task> result = taskController.getAllTasks();
        assertEquals(2, result.size());
        assertEquals("title1", result.get(0).getTitle());
        assertEquals("супер title 1", result.get(0).getDescription());
        assertEquals("title 2", result.get(1).getTitle());
        assertEquals("супер title 2", result.get(1).getDescription());
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Задача 1");
        task.setDescription("Описание задачи 1");

        when(taskService.getTaskById(1L)).thenReturn(task);
        Task result = taskController.getTaskById(1L);
        assertEquals(1L, result.getId().longValue());
        assertEquals("Задача 1", result.getTitle());
        assertEquals("Описание задачи 1", result.getDescription());
    }

    @Test
    public void testGetTasksByUserId() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Задача 1");
        task1.setDescription("Описание задачи 1");
        tasks.add(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Задача 2");
        task2.setDescription("Описание задачи 2");
        tasks.add(task2);

        when(taskService.findByUserId(1L)).thenReturn(tasks);
        List<Task> result = taskController.getTasksByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("Задача 1", result.get(0).getTitle());
        assertEquals("Описание задачи 1", result.get(0).getDescription());
        assertEquals("Задача 2", result.get(1).getTitle());
        assertEquals("Описание задачи 2", result.get(1).getDescription());
    }

    @Test
    public void testCreateTask() {
        // Создание новой задачи
        Task newTask = new Task();
        newTask.setTitle("Новая задача");
        newTask.setDescription("Описание новой задачи");
        when(taskService.createTask(any(Task.class))).thenReturn(newTask);
        Task result = taskController.createTask(newTask);
        assertEquals("Новая задача", result.getTitle());
        assertEquals("Описание новой задачи", result.getDescription());
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Задача 1");
        existingTask.setDescription("Описание задачи 1");
        Task updatedTask = new Task();
        updatedTask.setTitle("Обновленная задача");
        updatedTask.setDescription("Обновленное описание задачи");
        when(taskService.updateTask(1L, updatedTask)).thenReturn(updatedTask);
        Task result = taskController.updateTask(1L, updatedTask);
        assertEquals("Обновленная задача", result.getTitle());
        assertEquals("Обновленное описание задачи", result.getDescription());
    }

    @Test
    public void testDeleteTask() {
        taskController.deleteTask(1L);
        verify(taskService, times(1)).deleteTask(1L);
    }
}