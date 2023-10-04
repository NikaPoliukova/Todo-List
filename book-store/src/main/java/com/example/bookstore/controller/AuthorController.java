package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
  @Autowired
  private AuthorService authorService;

  @GetMapping
  public List<Author> getAllAuthors() {
    return authorService.getAllAuthors();
  }

  @GetMapping("/{id}")
  public Author getAuthorById(@PathVariable Long id) {
    return authorService.getAuthorById(id);
  }


  @PostMapping("/create")
  public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
    if (author == null || author.getName() == null || author.getName().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Author createdAuthor = authorService.createAuthor(author);
    return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
  }


  @PutMapping("/update")
  public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
    Author updatedAuthor = authorService.updateAuthor(author);
    return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public void deleteAuthor(@PathVariable Long id) {
    authorService.deleteAuthor(id);
  }
}


