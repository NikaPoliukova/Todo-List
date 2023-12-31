package com.example.todolist.service;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;

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
