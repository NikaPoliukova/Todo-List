package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "genre_id")
  private Genre genre;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private int quantity;

}

