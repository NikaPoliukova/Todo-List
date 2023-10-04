package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
  @Autowired
  private BookService bookService;

  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{id}")
  public Book getBookById(@PathVariable Long id) {
    return bookService.getBookById(id);
  }

  @GetMapping("/search")
  public List<Book> searchBooksByTitle(@RequestParam String keyword) {
    return bookService.searchBooksByTitle(keyword);
  }

  @GetMapping("/byAuthor/{authorId}")
  public List<Book> getBooksByAuthor(@PathVariable Long authorId) {
    Author author = new Author();
    author.setId(authorId);
    return bookService.getBooksByAuthor(author);
  }

  @GetMapping("/byGenre/{genreId}")
  public List<Book> getBooksByGenre(@PathVariable Long genreId) {
    Genre genre = new Genre();
    genre.setId(genreId);
    return bookService.getBooksByGenre(genre);
  }

  @PostMapping  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Book createBook(@RequestBody Book book) {
    return bookService.createBook(book);
  }

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    return bookService.updateBook(id, updatedBook);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }
}
