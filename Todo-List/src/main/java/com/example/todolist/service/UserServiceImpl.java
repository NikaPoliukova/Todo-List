package com.example.todolist.service;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.hendler.ResourceNotFoundException;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final TaskService taskService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, TaskService taskService) {
    this.userRepository = userRepository;
    this.taskService = taskService;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User updateUser(Long id, User updatedUser) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setUsername(updatedUser.getUsername());
      return userRepository.save(user);
    } else {
      throw new ResourceNotFoundException("User not found with id " + id);
    }
  }

  @Override
  public void deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new ResourceNotFoundException("User not found with id " + id);
    }
  }

  @Override
  public List<Task> getUserTasks(Long userId) {
    User user = getUserById(userId);
    return user.getTasks();
  }

  @Override
  public Task createUserTask(Long userId, Task task) {
    User user = getUserById(userId);
    task.setUser(user);
    return taskService.createTask(task);
  }

}
