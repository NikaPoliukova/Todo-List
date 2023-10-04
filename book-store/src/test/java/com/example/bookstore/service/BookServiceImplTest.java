import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

  @InjectMocks
  private BookServiceImpl bookService;

  @Mock
  private BookRepository bookRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllBooks() {
    List<Book> books = new ArrayList<>();
    when(bookRepository.findAll()).thenReturn(books);
    List<Book> result = bookService.getAllBooks();
    assertEquals(books, result);
  }

  @Test
  public void testGetBookById() {
    Long id = 1L;
    Book book = new Book();
    when(bookRepository.findById(id)).thenReturn(Optional.of(book));
    Book result = bookService.getBookById(id);
    assertEquals(book, result);
  }

  @Test
  public void testSearchBooksByTitle() {
    String keyword = "Java";
    List<Book> books = new ArrayList<>();
    when(bookRepository.findByTitleContaining(keyword)).thenReturn(books);
    List<Book> result = bookService.searchBooksByTitle(keyword);
    assertEquals(books, result);
  }

  @Test
  public void testGetBooksByAuthor() {
    Author author = new Author();
    author.setId(1L);
    List<Book> books = new ArrayList<>();
    when(bookRepository.findByAuthor(author)).thenReturn(books);
    List<Book> result = bookService.getBooksByAuthor(author);
    assertEquals(books, result);
  }

  @Test
  public void testGetBooksByGenre() {
    Genre genre = new Genre();
    genre.setId(1L);
    List<Book> books = new ArrayList<>();
    when(bookRepository.findByGenre(genre)).thenReturn(books);
    List<Book> result = bookService.getBooksByGenre(genre);
    assertEquals(books, result);
  }

  @Test
  public void testCreateBook() {
    Book bookToCreate = new Book();
    when(bookRepository.save(bookToCreate)).thenReturn(bookToCreate);
    Book result = bookService.createBook(bookToCreate);
    assertEquals(bookToCreate, result);
  }

  @Test
  public void testUpdateBook() {
    Long id = 1L;
    Book updatedBook = new Book();
    when(bookRepository.existsById(id)).thenReturn(true);
    when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
    Book result = bookService.updateBook(id, updatedBook);
    assertEquals(updatedBook, result);
  }

  @Test
  public void testUpdateBook_NotFound() {
    Long id = 1L;
    Book updatedBook = new Book();
    when(bookRepository.existsById(id)).thenReturn(false);
    assertThrows(ResponseStatusException.class, () -> bookService.updateBook(id, updatedBook));
  }

  @Test
  public void testDeleteBook() {
    Long id = 1L;
    doNothing().when(bookRepository).deleteById(id);
    assertDoesNotThrow(() -> bookService.deleteBook(id));
  }

  @Test
  public void testDeleteBook_NotFound() {
    Long id = 1L;
    doThrow(EmptyResultDataAccessException.class).when(bookRepository).deleteById(id);
    assertThrows(ResponseStatusException.class, () -> bookService.deleteBook(id));
  }
}