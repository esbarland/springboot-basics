package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BookRestController {

    private final BookRepository repository;

    public BookRestController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/book")
    public String get(@RequestParam String name, @RequestParam Integer pages) {
        BookEntity existingBook = repository.findByNameAndPages(name, pages);
        if (existingBook != null) {
            return "Livre déjà existant";
        }

        BookEntity book = BookEntity.builder().name(name).pages(pages).build();
        repository.save(book);

        return "Livre crée avec succès";
    }
}
