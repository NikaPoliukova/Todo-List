package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;
import com.example.bookstore.repository.BookRepository;
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
public class BookServiceImpl implements BookService {
  @Autowired
  private BookRepository bookRepository;

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Override
  public Book getBookById(Long id) {
    return bookRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
  }

  @Override
  public List<Book> searchBooksByTitle(String keyword) {
    return bookRepository.findByTitleContaining(keyword);
  }

  @Override
  public List<Book> getBooksByAuthor(Author author) {
    return bookRepository.findByAuthor(author);
  }

  @Override
  public List<Book> getBooksByGenre(Genre genre) {
    try {
      return bookRepository.findByGenre(genre);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching books by genre", ex);
    }
  }

  @Override
  public Book createBook(Book book) {
    try {
      return bookRepository.save(book);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid book data", e);
    }
  }

  @Override
  public Book updateBook(Long id, Book updatedBook) {
    if (!bookRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }
    updatedBook.setId(id);
    try {
      return bookRepository.save(updatedBook);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid book data", e);
    }
  }

  @Override
  public void deleteBook(Long id) {
    try {
      bookRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting book", e);
    }
  }
}
