package com.example.bookstore.service;

import com.example.bookstore.entity.Genre;
import com.example.bookstore.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenreServiceImplTest {

  @InjectMocks
  private GenreServiceImpl genreService;

  @Mock
  private GenreRepository genreRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllGenres() {
    List<Genre> genres = new ArrayList<>();
    genres.add(new Genre(1L, "Science Fiction"));
    genres.add(new Genre(2L, "Fantasy"));

    when(genreRepository.findAll()).thenReturn(genres);
    List<Genre> result = genreService.getAllGenres();
    assertEquals(genres, result);
  }

  @Test
  public void testGetGenreById() {
    Genre genre = new Genre(1L, "Science Fiction");
    when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
    Genre result = genreService.getGenreById(1L);
    assertEquals(genre, result);
  }

  @Test
  public void testCreateGenre() {
    Genre genreToCreate = new Genre(null, "Horror");
    when(genreRepository.save(genreToCreate)).thenReturn(new Genre(1L, "Horror"));
    Genre result = genreService.createGenre(genreToCreate);
    assertEquals("Horror", result.getName());
    assertNotNull(result.getId());
  }

  @Test
  public void testUpdateGenre() {
    Genre genreToUpdate = new Genre(1L, "Fantasy");

    when(genreRepository.save(genreToUpdate)).thenReturn(new Genre(1L, "Fantasy"));
    Genre result = genreService.updateGenre(genreToUpdate);
    assertEquals("Fantasy", result.getName());
    assertEquals(1L, result.getId().longValue());
  }

  @Test
  public void testDeleteGenre() {
    doNothing().when(genreRepository).deleteById(1L);
    assertDoesNotThrow(() -> genreService.deleteGenre(1L));
  }

  @Test
  public void testDeleteGenre_NotFound() {
    doThrow(EmptyResultDataAccessException.class).when(genreRepository).deleteById(1L);
    assertThrows(ResponseStatusException.class, () -> genreService.deleteGenre(1L));
  }
}