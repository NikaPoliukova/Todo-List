package com.example.bookstore.controller;

import com.example.bookstore.entity.Genre;
import com.example.bookstore.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class GenreControllerTest {

  private MockMvc mockMvc;

  @Mock
  private GenreService genreService;

  @InjectMocks
  private GenreController genreController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
  }

  @Test
  public void testGetAllGenres() throws Exception {
    List<Genre> genres = Arrays.asList(new Genre(), new Genre());

    when(genreService.getAllGenres()).thenReturn(genres);
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/genres")
        .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void testGetGenreById_Found() throws Exception {
    Genre genre = new Genre();
    genre.setId(1L);
    when(genreService.getGenreById(1L)).thenReturn(genre);
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/genres/1")
        .contentType(MediaType.APPLICATION_JSON));
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  public void testGetGenreById_NotFound() throws Exception {
    when(genreService.getGenreById(1L)).thenReturn(null);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/genres/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }


  @Test
  public void testDeleteGenre_NoContent() throws Exception {
    doNothing().when(genreService).deleteGenre(1L);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/genres/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  public void testDeleteGenre_NotFound() throws Exception {
    doThrow(ResponseStatusException.class).when(genreService).deleteGenre(1L);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/genres/1"))
        .andExpect(status().isNotFound());
  }
}

