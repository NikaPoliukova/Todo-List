package com.example.bookstore.controller;


import com.example.bookstore.entity.Author;
import com.example.bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthorControllerTest {


  @Mock
  private AuthorService authorService;

  @InjectMocks
  private AuthorController authorController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
  }

  @Test
  public void testGetAllAuthors() throws Exception {
    List<Author> authors = new ArrayList<>();
    authors.add(new Author(1L, "John Doe"));
    authors.add(new Author(2L, "Jane Smith"));

    when(authorService.getAllAuthors()).thenReturn(authors);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/authors"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2))) //
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("John Doe"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("Jane Smith"));
  }

  @Test
  public void testGetAuthorById() throws Exception {
    Author author = new Author(1L, "John Doe");
    when(authorService.getAuthorById(1L)).thenReturn(author);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("John Doe"));
  }

  @Test
  public void testDeleteAuthor() throws Exception {
    doNothing().when(authorService).deleteAuthor(1L);
    mockMvc.perform(delete("/api/authors/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void testCreateAuthor_InvalidData() throws Exception {
    mockMvc.perform(post("/api/authors/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":null}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDeleteAuthor_NotFound() throws Exception {
    doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"))
        .when(authorService).deleteAuthor(1L);
    mockMvc.perform(delete("/api/authors/1"))
        .andExpect(status().isNotFound());
  }
}
