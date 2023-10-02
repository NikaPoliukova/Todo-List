package com.example.todolist.controller;


import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable Long userId) {
    return userService.getUserById(userId);
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PutMapping("/{userId}")
  public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
    return userService.updateUser(userId, updatedUser);
  }

  @DeleteMapping("/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
  }

  @GetMapping("/{userId}/tasks")
  public List<Task> getUserTasks(@PathVariable Long userId) {
    return userService.getUserTasks(userId);
  }

  @PostMapping("/{userId}/tasks")
  public Task createUserTask(@PathVariable Long userId, @RequestBody Task task) {
    return userService.createUserTask(userId, task);
  }
}

