package com.basics.library.book.controllers;

import com.basics.library.book.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping("/book")
    public String get(@RequestParam String name, @RequestParam Integer pages) {
        return service.createBook(name, pages);
    }
}
