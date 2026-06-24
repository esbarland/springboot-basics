package com.basics.library.book.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book")
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String isbn;

    String name;

    String author;

    Integer year;

    Integer pages;

    String description;

    @Enumerated(EnumType.STRING)
    BookCategory category;

    @Enumerated(EnumType.STRING)
    BookStatus status;

    @Setter
    Integer rating;
}
