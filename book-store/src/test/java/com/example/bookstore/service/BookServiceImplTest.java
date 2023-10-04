package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {
  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private AuthorRepository authorRepository;

  @MockBean
  private GenreRepository genreRepository;

  private BookService bookService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    bookService = new BookServiceImpl(bookRepository);
  }

  @Test
  public void testGetAllBooks() {
    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Book 1");

    Book book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Book 2");

    List<Book> books = Arrays.asList(book1, book2);

    when(bookRepository.findAll()).thenReturn(books);
    List<Book> result = bookService.getAllBooks();
    assertEquals(2, result.size());
    assertEquals("Book 1", result.get(0).getTitle());
    assertEquals("Book 2", result.get(1).getTitle());
  }

  @Test
  public void testGetBookById() {
    Book book = new Book();
    book.setId(1L);
    book.setTitle("Book 1");

    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    Book result = bookService.getBookById(1L);
    assertNotNull(result);
    assertEquals("Book 1", result.getTitle());
  }

  @Test
  public void testSearchBooksByTitle() {
    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Java Programming");

    Book book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Python Programming");
    List<Book> books = Arrays.asList(book1, book2);

    when(bookRepository.findByTitleContaining("Programming")).thenReturn(books);
    List<Book> result = bookService.searchBooksByTitle("Programming");
    assertEquals(2, result.size());
    assertEquals("Java Programming", result.get(0).getTitle());
    assertEquals("Python Programming", result.get(1).getTitle());
  }

  @Test
  public void testGetBooksByAuthor() {
    Author author = new Author();
    author.setId(1L);
    author.setName("Author 1");

    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Book 1");
    book1.setAuthor(author);

    Book book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Book 2");
    book2.setAuthor(author);

    List<Book> booksByAuthor = Arrays.asList(book1, book2);

    when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
    when(bookRepository.findByAuthor(author)).thenReturn(booksByAuthor);
    List<Book> result = bookService.getBooksByAuthor(author);
    assertEquals(2, result.size());
    assertEquals("Book 1", result.get(0).getTitle());
    assertEquals("Book 2", result.get(1).getTitle());
  }

  @Test
  public void testGetBooksByGenre() {
    Genre genre = new Genre();
    genre.setId(1L);
    genre.setName("Fiction");

    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Book 1");
    book1.setGenre(genre);

    Book book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Book 2");
    book2.setGenre(genre);

    List<Book> booksByGenre = Arrays.asList(book1, book2);
    when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
    when(bookRepository.findByGenre(genre)).thenReturn(booksByGenre);
    List<Book> result = bookService.getBooksByGenre(genre);
    assertEquals(2, result.size());
    assertEquals("Book 1", result.get(0).getTitle());
    assertEquals("Book 2", result.get(1).getTitle());
  }

  @Test
  void testCreateBook_ValidBook() {
    Book bookToCreate = new Book();
    bookToCreate.setTitle("Sample Book");

    when(bookRepository.save(any(Book.class))).thenReturn(bookToCreate);
    Book createdBook = bookService.createBook(bookToCreate);
    assertEquals(bookToCreate, createdBook);
  }

  @Test
  void testCreateBook_DuplicateTitle() {
    Book bookToCreate = new Book();
    bookToCreate.setTitle("Sample Book");
    when(bookRepository.save(any(Book.class))).thenThrow(DataIntegrityViolationException.class);
    assertThrows(ResponseStatusException.class, () -> {
      bookService.createBook(bookToCreate);
    });
  }

  @Test
  void testUpdateBook_ValidBook() {
    Book updatedBook = new Book();
    updatedBook.setId(1L);
    updatedBook.setTitle("Updated Book");

    when(bookRepository.existsById(1L)).thenReturn(true);
    when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
    Book result = bookService.updateBook(1L, updatedBook);
    assertEquals(updatedBook, result);
  }

  @Test
  void testUpdateBook_BookNotFound() {
    Book updatedBook = new Book();
    updatedBook.setId(1L);
    updatedBook.setTitle("Updated Book");

    when(bookRepository.existsById(1L)).thenReturn(false);
    assertThrows(ResponseStatusException.class, () -> {
      bookService.updateBook(1L, updatedBook);
    });
  }

  @Test
  public void testDeleteBook() {
    bookService.deleteBook(1L);
    verify(bookRepository, times(1)).deleteById(1L);
  }

  @Test
  public void testGetBooksByAuthorNotFound() {
    Author author = new Author();
    author.setId(1L);
    author.setName("Author 1");

    when(authorRepository.findById(1L)).thenReturn(Optional.empty());
    List<Book> result = bookService.getBooksByAuthor(author);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testGetBooksByGenreNotFound() {
    Genre genre = new Genre();
    genre.setId(1L);
    genre.setName("Fiction");

    when(genreRepository.findById(1L)).thenReturn(Optional.empty());
    List<Book> result = bookService.getBooksByGenre(genre);
    assertTrue(result.isEmpty());
  }
}