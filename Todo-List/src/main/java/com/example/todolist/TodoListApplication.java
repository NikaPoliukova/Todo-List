package com.example.todolist;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TodoListApplication {
  @Autowired
  private TaskService taskService;

  public TodoListApplication() {
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoListApplication.class, args);
  }
}

