package org.example.service;

import org.example.entity.Task;
import org.example.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    List<Task> getUserTasks(Long userId);
    Task createUserTask(Long userId, Task task);
}