package com.basics.library.book.services;

import com.basics.library.book.models.BookEntity;
import com.basics.library.book.models.exception.BookCreationException;
import com.basics.library.book.models.exception.BookNotFoundException;
import com.basics.library.book.persistence.BookRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookEntity createBook(String name, String isbn, Integer pages, Integer year, String description) throws BookCreationException {
        if (isbn == null || StringUtils.isBlank(isbn)) {
            throw new BookCreationException("ISBN cannot be null or empty");
        }
        if (!BookService.isValidIsbn13(isbn)) {
            throw new BookCreationException("Invalid ISBN");
        }
        if (name == null || StringUtils.isBlank(name)) {
            throw new BookCreationException("Book name cannot be null or empty");
        }
        if (pages == null || pages <= 0) {
            throw new BookCreationException("The book must contains at least one page");
        }
        if (year == null || year > Year.now().getValue()) {
            throw new BookCreationException("The publication year cannot be later than the current year");
        }

        BookEntity existingBook = repository.findByIsbn(isbn);
        if (existingBook != null) {
            throw new BookCreationException("This book already exists");
        }

        BookEntity book = BookEntity.builder().isbn(isbn).name(name).pages(pages).year(year).description(description).build();
        return repository.save(book);
    }

    public BookEntity getBookById(Long id) throws BookNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found this id"));
    }

    public List<BookEntity> getAllBooks() {
        return repository.findAll();
    }

    /**
     * Vérifie qu'un ISBN-13 (norme appliquée depuis 2007) est valide.
     * Un ISBN-13 est composé de 13 chiffres, le dernier étant une clé de
     * contrôle calculée par pondération alternée des 12 premiers (1 puis 3).
     *
     * @param isbn l'ISBN à vérifier (les tirets et espaces sont ignorés)
     * @return true si l'ISBN-13 est valide, false sinon
     */
    private static boolean isValidIsbn13(String isbn) {
        if (isbn == null) {
            return false;
        }

        String digits = isbn.replaceAll("[\\s-]", "");
        if (!digits.matches("\\d{13}")) {
            return false;
        }

        return true;
//        int sum = 0;
//        for (int i = 0; i < 12; i++) {
//            int digit = digits.charAt(i) - '0';
//            sum += (i % 2 == 0) ? digit : digit * 3;
//        }
//
//        int checkDigit = (10 - (sum % 10)) % 10;
//        return checkDigit == (digits.charAt(12) - '0');
    }
}
