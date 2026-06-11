package com.basics.library.book.controllers;

import com.basics.library.book.dto.BookDTO;
import com.basics.library.book.models.BookEntity;
import com.basics.library.book.models.exception.BookCreationException;
import com.basics.library.book.services.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String get(@RequestParam String name, @RequestParam Integer pages) {
        return "OK GET";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO.PostOutput post(@Valid @RequestBody BookDTO.PostInput input) throws BookCreationException {
        BookEntity createdBook = service.createBook(input.getName(), input.getIsbn(), input.getPages(), input.getYear(), input.getDescription());
        return BookDTO.PostOutput.builder().id(createdBook.getId()).name(createdBook.getName()).isbn(createdBook.getIsbn()).pages(createdBook.getPages()).year(createdBook.getYear()).description(createdBook.getDescription()).build();
    }
}
