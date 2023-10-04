package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  @Override
  public List<Author> getAllAuthors() {
    try {
      return authorRepository.findAll();
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching authors", ex);
    }
  }

  @Override
  public Author getAuthorById(Long id) {
    try {
      return authorRepository.findById(id).orElse(null);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching author by id", ex);
    }
  }

  @Override
  public Author createAuthor(Author author) {
    try {
      return authorRepository.save(author);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating author", ex);
    }
  }

  @Override
  public Author updateAuthor(Author author) {
    try {
      if (author.getId() == null || !authorRepository.existsById(author.getId())) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
      }
      return authorRepository.save(author);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while updating author", ex);
    }
  }

  @Override
  public void deleteAuthor(Long id) {
    try {
      authorRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found", ex);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting author", ex);
    }
  }
}
