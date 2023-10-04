package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {
  @InjectMocks
  private BookController bookController;
  @Mock
  private BookService bookService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
  }

  @Test
  public void testGetAllBooks() throws Exception {
    List<Book> books = new ArrayList<>();
    when(bookService.getAllBooks()).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testGetBookById() throws Exception {
    Long id = 1L;
    Book book = new Book();
    when(bookService.getBookById(id)).thenReturn(book);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testSearchBooksByTitle() throws Exception {
    String keyword = "Java";
    List<Book> books = new ArrayList<>();
    when(bookService.searchBooksByTitle(keyword)).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search")
            .param("keyword", keyword))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testGetBooksByAuthor() throws Exception {
    Long authorId = 1L;
    List<Book> books = new ArrayList<>();
    when(bookService.getBooksByAuthor(any(Author.class))).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/byAuthor/{authorId}", authorId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testGetBooksByGenre() throws Exception {
    Long genreId = 1L;
    List<Book> books = new ArrayList<>();
    when(bookService.getBooksByGenre(any(Genre.class))).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/byGenre/{genreId}",
            genreId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }


  @Test
  public void testCreateBook() throws Exception {
    Book bookToCreate = new Book();
    bookToCreate.setTitle("Book Title");
    when(bookService.createBook(any(Book.class))).thenReturn(bookToCreate);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Book Title\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("Book Title"));
  }

  @Test
  public void testUpdateBook() throws Exception {
    Long bookId = 1L;
    Book updatedBook = new Book();
    when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(updatedBook);
    mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Updated Book Title\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testDeleteBook() throws Exception {
    Long bookId = 1L;
    doNothing().when(bookService).deleteBook(bookId);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookId))
        .andExpect(status().isOk());
  }
}