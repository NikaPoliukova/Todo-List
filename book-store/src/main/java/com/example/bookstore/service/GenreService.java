package com.example.bookstore.service;

import com.example.bookstore.entity.Genre;

import java.util.List;

public interface GenreService {
  List<Genre> getAllGenres();

  Genre getGenreById(Long id);

  public Genre createGenre(Genre genre);

  public Genre updateGenre(Genre genre);

  void deleteGenre(Long id);
}

