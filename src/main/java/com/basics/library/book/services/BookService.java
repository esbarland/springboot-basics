package com.basics.library.book.services;

import com.basics.library.book.models.BookEntity;
import com.basics.library.book.persistence.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public String createBook(String name, Integer pages) {
        BookEntity existingBook = repository.findByNameAndPages(name, pages);
        if (existingBook != null) {
            return "Livre déjà existant";
        }

        BookEntity book = BookEntity.builder().name(name).pages(pages).build();
        repository.save(book);
        return "Livre crée avec succès";
    }
}
