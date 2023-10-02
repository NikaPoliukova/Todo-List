package com.example.TodoList.controller;

import com.example.TodoList.entity.Task;
import com.example.TodoList.entity.User;
import com.example.TodoList.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(users);
        List<User> result = userController.getAllUsers();
        verify(userService, times(1)).getAllUsers();
        assertEquals(users, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);
        User result = userController.getUserById(1L);
        verify(userService, times(1)).getUserById(1L);
        assertEquals(user, result);
    }

    @Test
    public void testCreateUser() {
        User newUser = new User();
        newUser.setUsername("New User");
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("New User");
        when(userService.createUser(newUser)).thenReturn(expectedUser);
        User result = userController.createUser(newUser);
        verify(userService, times(1)).createUser(newUser);
        assertEquals(expectedUser, result);
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setUsername("Updated User");
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("Updated User");
        when(userService.updateUser(1L, updatedUser)).thenReturn(expectedUser);
        User result = userController.updateUser(1L, updatedUser);
        verify(userService, times(1)).updateUser(1L, updatedUser);
        assertEquals(expectedUser, result);
    }

    @Test
    public void testDeleteUser() {
        userController.deleteUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void testGetUserTasks() {
        List<Task> tasks = new ArrayList<>();
        when(userService.getUserTasks(1L)).thenReturn(tasks);
        List<Task> result = userController.getUserTasks(1L);
        verify(userService, times(1)).getUserTasks(1L);
        assertEquals(tasks, result);
    }

    @Test
    public void testCreateUserTask() {
        Task newTask = new Task();
        newTask.setTitle("New Task");
        newTask.setDescription("Description");
        Task expectedTask = new Task();
        expectedTask.setId(1L);
        expectedTask.setTitle("New Task");
        expectedTask.setDescription("Description");
        when(userService.createUserTask(1L, newTask)).thenReturn(expectedTask);
        Task result = userController.createUserTask(1L, newTask);
        verify(userService, times(1)).createUserTask(1L, newTask);
        assertEquals(expectedTask, result);
    }
}
