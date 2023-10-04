package com.example.bookstore.service;

import com.example.bookstore.entity.Genre;
import com.example.bookstore.repository.GenreRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
  @Autowired
  private GenreRepository genreRepository;

  @Override
  public List<Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  @Override
  public Genre getGenreById(Long id) {
    return genreRepository.findById(id).orElse(null);
  }

  @Override
  public Genre createGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  @Override
  public Genre updateGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  @Override
  public void deleteGenre(Long id) {
    try {
      genreRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found", ex);
    }
  }
}


