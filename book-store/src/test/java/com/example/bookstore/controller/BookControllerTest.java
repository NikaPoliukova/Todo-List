package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;
import com.example.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BookControllerTest {

  @Mock
  private BookService bookService;

  @InjectMocks
  private BookController bookController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
  }

  @Test
  public void testGetAllBooks() throws Exception {
    // Создаем макет (mock) данных
    List<Book> books = new ArrayList<>();
    books.add(new Book(1L, "Book 1", new Author(), new Genre(), 10.0, 100));
    books.add(new Book(2L, "Book 2", new Author(), new Genre(), 15.0, 150));

    when(bookService.getAllBooks()).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2)) // Проверяем количество книг в ответе
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Book 1"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].title").value("Book 2"));
  }

  @Test
  public void testGetBookById() throws Exception {
    Book book = new Book(1L, "Book 1", new Author(), new Genre(), 10.0, 100);

    when(bookService.getBookById(1L)).thenReturn(book);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("Book 1"));
  }

  @Test
  public void testSearchBooksByTitle() throws Exception {
    List<Book> books = new ArrayList<>();
    books.add(new Book(1L, "Book 1", new Author(), new Genre(), 10.0, 100));
    books.add(new Book(2L, "Book 2", new Author(), new Genre(), 15.0, 150));

    when(bookService.searchBooksByTitle("Book")).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search?keyword=Book"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Book 1"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].title").value("Book 2"));
  }

  @Test
  public void testGetBooksByAuthor() throws Exception {
    Author author = new Author();
    author.setId(1L);
    List<Book> books = new ArrayList<>();
    books.add(new Book(1L, "Book 1", author, new Genre(), 10.0, 100));
    when(bookService.getBooksByAuthor(author)).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/byAuthor/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Book 1"));
  }

  @Test
  public void testGetBooksByGenre() throws Exception {
    Genre genre = new Genre();
    genre.setId(1L);
    List<Book> books = new ArrayList<>();
    books.add(new Book(1L, "Book 1", new Author(), genre, 10.0, 100));
    when(bookService.getBooksByGenre(genre)).thenReturn(books);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/byGenre/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Book 1"));
  }

  @Test
  public void testDeleteBook() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
        .andExpect(status().isOk());
  }

  @Test
  void testCreateBook_ValidBook() throws Exception {
    Book bookToCreate = new Book();
    bookToCreate.setTitle("Sample Book");

    when(bookService.createBook(any(Book.class))).thenReturn(bookToCreate);
    mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookToCreate)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("Sample Book"));
  }

  @Test
  void testUpdateBook_ValidBook() throws Exception {
    Book updatedBook = new Book();
    updatedBook.setId(1L);
    updatedBook.setTitle("Updated Book");

    when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);
    mockMvc.perform(put("/api/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedBook)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Updated Book"));
  }
}