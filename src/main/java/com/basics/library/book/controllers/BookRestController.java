package com.basics.library.book.controllers;

import com.basics.library.book.dto.BookDTO;
import com.basics.library.book.services.BookService;
import lombok.extern.slf4j.Slf4j;
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
    public String get(@RequestParam String name, @RequestParam Integer pages) {
        return "OK GET";
    }

    @PostMapping
    public String post(@RequestBody BookDTO.PostInput input){
        return service.createBook(input.getName(), input.getPages());
    }
}
