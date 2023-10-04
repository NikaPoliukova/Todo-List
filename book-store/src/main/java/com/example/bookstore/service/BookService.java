package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Genre;

import java.util.List;

public interface BookService {
  List<Book> getAllBooks();

  Book getBookById(Long id);

  List<Book> searchBooksByTitle(String keyword);

  List<Book> getBooksByAuthor(Author author);

  List<Book> getBooksByGenre(Genre genre);

  Book createBook(Book book);

  Book updateBook(Long id, Book updatedBook);

  void deleteBook(Long id);
}

