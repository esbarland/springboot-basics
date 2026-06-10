package com.basics.library.book.services;

import com.basics.library.book.models.BookEntity;
import com.basics.library.book.persistence.BookRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public String createBook(String name, Integer pages) {
        if(name == null || StringUtils.isBlank(name)) {
            return "Le nom du livre ne dois pas être vide";
        }
        if(pages == null || pages <= 0) {
            return "Le livre doit contenir au moins une page";
        }

        BookEntity existingBook = repository.findByNameAndPages(name, pages);
        if (existingBook != null) {
            return "Livre déjà existant";
        }

        BookEntity book = BookEntity.builder().name(name).pages(pages).build();
        repository.save(book);
        return "Livre crée avec succès";
    }
}
