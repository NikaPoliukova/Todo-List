package com.example.TodoList.controller;

import com.example.TodoList.entity.Task;
import com.example.TodoList.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        // Здесь добавьте тестовые задачи в список tasks

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(tasks.size())));
    }

    @Test
    public void testGetTasksByUserId() throws Exception {
        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();
        // Здесь добавьте тестовые задачи в список tasks

        when(taskService.findByUserId(userId)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/byUserId")
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(tasks.size())));
    }

    @Test
    public void testGetTaskById() throws Exception {
        Long taskId = 1L;
        Task task = new Task();
        // Установите значения полей для тестовой задачи

        when(taskService.getTaskById(taskId)).thenReturn(task);

        mockMvc.perform(get("/tasks/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.description").value(task.getDescription()));
    }

    @Test
    public void testCreateTask() throws Exception {
        Task taskToCreate = new Task();
        // Установите значения полей для задачи, которую вы хотите создать

        when(taskService.createTask(any(Task.class))).thenReturn(taskToCreate);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskToCreate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskToCreate.getId()))
                .andExpect(jsonPath("$.description").value(taskToCreate.getDescription()));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Long taskId = 1L;
        Task updatedTask = new Task();
        // Установите значения полей для обновленной задачи

        when(taskService.updateTask(eq(taskId), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedTask.getId()))
                .andExpect(jsonPath("$.description").value(updatedTask.getDescription()));
    }

    @Test
    public void testDeleteTask() throws Exception {
        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/tasks/{taskId}", taskId))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(taskId);
    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}