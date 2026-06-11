package com.basics.library.book.services;

import com.basics.library.book.models.BookEntity;
import com.basics.library.book.models.exception.BookCreationException;
import com.basics.library.book.persistence.BookRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookEntity createBook(String name, Integer pages) throws BookCreationException {
        if (name == null || StringUtils.isBlank(name)) {
            throw new BookCreationException("Book name cannot be null or empty");
        }
        if (pages == null || pages <= 0) {
            throw new BookCreationException("The book must contains at least one page");
        }

        BookEntity existingBook = repository.findByNameAndPages(name, pages);
        if (existingBook != null) {
            throw new BookCreationException("This book already exists");

        }

        BookEntity book = BookEntity.builder().name(name).pages(pages).build();
        return repository.save(book);
    }
}
