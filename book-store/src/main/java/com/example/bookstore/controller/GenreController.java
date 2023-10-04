package com.example.bookstore.controller;

import com.example.bookstore.entity.Genre;
import com.example.bookstore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
  @Autowired
  private GenreService genreService;

  @GetMapping
  public ResponseEntity<List<Genre>> getAllGenres() {
    List<Genre> genres = genreService.getAllGenres();
    return new ResponseEntity<>(genres, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
    Genre genre = genreService.getGenreById(id);
    if (genre != null) {
      return new ResponseEntity<>(genre, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
    // Проверка на валидность данных жанра с использованием аннотации @Valid
    Genre createdGenre = genreService.createGenre(genre);
    return new ResponseEntity<>(createdGenre, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
    Genre updatedGenre = genreService.updateGenre(genre);
    return new ResponseEntity<>(updatedGenre, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
    try {
      genreService.deleteGenre(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ResponseStatusException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}

