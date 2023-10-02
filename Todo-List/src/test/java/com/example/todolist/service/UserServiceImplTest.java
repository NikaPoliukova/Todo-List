package com.example.todolist.service;

import com.example.todolist.entity.User;
import com.example.todolist.hendler.ResourceNotFoundException;
import com.example.todolist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {

        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "User1", new ArrayList<>()));
        userList.add(new User(2L, "User2", new ArrayList<>()));
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getAllUsers();
        assertEquals(userList, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "User1", new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertEquals(user, result);
    }

    @Test
    public void testCreateUser() {
        User user = new User(1L, "User1", new ArrayList<>());
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.createUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User(1L, "User1", new ArrayList<>());
        User updatedUser = new User(1L, "UpdatedUser1", new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        User result = userService.updateUser(1L, updatedUser);
        assertEquals(updatedUser, result);
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(1L, new User());
        });
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
    }
}
