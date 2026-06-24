package com.basics.library.book.controllers;

import com.basics.library.book.dto.BookDTO;
import com.basics.library.book.models.BookEntity;
import com.basics.library.book.models.exception.BookValidationException;
import com.basics.library.book.services.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<BookDTO.PostOutput> getAll(@RequestParam(required = false) String search) {
        return service.getAllBooks(search).stream().map(this::toOutput).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO.PostOutput getById(@PathVariable Long id) {
        return toOutput(service.getBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO.PostOutput post(@Valid @RequestBody BookDTO.PostInput input) throws BookValidationException {
        BookEntity createdBook = service.createBook(input.getName(), input.getAuthor(), input.getIsbn(), input.getPages(), input.getYear(), input.getDescription(), input.getRating(), input.getCategory(), input.getStatus());
        return toOutput(createdBook);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO.PostOutput put(@PathVariable Long id, @Valid @RequestBody BookDTO.PostInput input) throws BookValidationException {
        BookEntity updatedBook = service.updateBook(id, input.getName(), input.getAuthor(), input.getIsbn(), input.getPages(), input.getYear(), input.getDescription(), input.getRating(), input.getCategory(), input.getStatus());
        return toOutput(updatedBook);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteBook(id);
    }

    @PutMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO.PostOutput rate(@PathVariable Long id, @Valid @RequestBody BookDTO.RatingInput input) {
        return toOutput(service.rateBook(id, input.getRating()));
    }

    private BookDTO.PostOutput toOutput(BookEntity book) {
        return BookDTO.PostOutput.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .pages(book.getPages())
                .year(book.getYear())
                .description(book.getDescription())
                .category(book.getCategory())
                .status(book.getStatus())
                .rating(book.getRating())
                .build();
    }
}
