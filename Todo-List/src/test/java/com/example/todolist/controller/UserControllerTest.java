package com.example.todolist.controller;

import com.example.todolist.entity.User;
import com.example.todolist.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testGetAllUsers() throws Exception {
    List<User> userList = new ArrayList<>();
    when(userService.getAllUsers()).thenReturn(userList);
    mockMvc.perform(MockMvcRequestBuilders.get("/users")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  public void testGetUserById() throws Exception {
    Long userId = 1L;
    User user = new User();
    when(userService.getUserById(userId)).thenReturn(user);
    mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  public void testCreateUser() throws Exception {
    User user = new User();
    when(userService.createUser(any(User.class))).thenReturn(user);

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(user)))
            .andExpect(status().isOk());
  }

  @Test
  public void testUpdateUser() throws Exception {
    Long userId = 1L;
    User updatedUser = new User();
    when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(updatedUser)))
            .andExpect(status().isOk());
  }

  @Test
  public void testDeleteUser() throws Exception {
    Long userId = 1L;
    mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId))
            .andExpect(status().isOk());
  }

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
