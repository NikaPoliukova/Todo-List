package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorServiceImplTest {

  @InjectMocks
  private AuthorServiceImpl authorService;

  @Mock
  private AuthorRepository authorRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllAuthors() {
    List<Author> authors = new ArrayList<>();
    authors.add(new Author(1L, "Author1"));
    authors.add(new Author(2L, "Author2"));
    when(authorRepository.findAll()).thenReturn(authors);
    List<Author> result = authorService.getAllAuthors();
    assertEquals(2, result.size());
    assertEquals("Author1", result.get(0).getName());
    assertEquals("Author2", result.get(1).getName());
  }

  @Test
  public void testGetAuthorById() {
    Author author = new Author(1L, "Author1");
    when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
    Author result = authorService.getAuthorById(1L);
    assertNotNull(result);
    assertEquals("Author1", result.getName());
  }

  @Test
  public void testGetAuthorById_NotFound() {
    when(authorRepository.findById(1L)).thenReturn(Optional.empty());
    Author result = authorService.getAuthorById(1L);
    assertNull(result);
  }

  @Test
  public void testCreateAuthor_Success() {
    Author authorToCreate = new Author();
    authorToCreate.setName("John Doe");
    Author savedAuthor = new Author();
    savedAuthor.setId(1L);
    savedAuthor.setName("John Doe");
    when(authorRepository.save(authorToCreate)).thenReturn(savedAuthor);
    Author createdAuthor = authorService.createAuthor(authorToCreate);
    assertNotNull(createdAuthor);
    assertEquals(1L, createdAuthor.getId().longValue());
    assertEquals("John Doe", createdAuthor.getName());
  }

  @Test
  public void testUpdateAuthor_Success() {
    Author existingAuthor = new Author();
    existingAuthor.setId(1L);
    existingAuthor.setName("John Doe");
    when(authorRepository.existsById(1L)).thenReturn(true);
    when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);
    Author updatedAuthor = authorService.updateAuthor(existingAuthor);
    assertNotNull(updatedAuthor);
    assertEquals(1L, updatedAuthor.getId().longValue());
    assertEquals("John Doe", updatedAuthor.getName());
  }

  @Test
  public void testDeleteAuthor() {
    authorService.deleteAuthor(1L);
    verify(authorRepository).deleteById(1L);
  }
}

