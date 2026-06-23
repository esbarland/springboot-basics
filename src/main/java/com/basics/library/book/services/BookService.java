package com.basics.library.book.services;

import com.basics.library.book.models.BookEntity;
import com.basics.library.book.models.exception.BookValidationException;
import com.basics.library.book.models.exception.BookNotFoundException;
import com.basics.library.book.models.exception.InvalidRatingException;
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

    public BookEntity createBook(String name, String isbn, Integer pages, Integer year, String description, Integer rating) throws BookValidationException {
        validateBook(null, name, isbn, pages, year);
        validateRating(rating);
        BookEntity book = BookEntity.builder().isbn(isbn).name(name).pages(pages).year(year).description(description).rating(rating).build();
        return repository.save(book);
    }

    public BookEntity updateBook(Long id, String name, String isbn, Integer pages, Integer year, String description, Integer rating) throws BookNotFoundException, BookValidationException {
        BookEntity existing = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found with this id"));
        validateBook(id, name, isbn, pages, year);
        validateRating(rating);
        return repository.save(BookEntity.builder()
                .id(existing.getId())
                .isbn(isbn)
                .name(name)
                .pages(pages)
                .year(year)
                .description(description)
                .rating(rating)
                .build());
    }

    public BookEntity rateBook(Long id, Integer rating) throws BookNotFoundException, InvalidRatingException {
        if (rating == null) {
            throw new InvalidRatingException("The rating must be between 1 and 5 stars");
        }
        validateRating(rating);
        BookEntity book = getBookById(id);
        book.setRating(rating);
        return repository.save(book);
    }

    public void deleteBook(Long id) throws BookNotFoundException {
        BookEntity book = getBookById(id);
        repository.delete(book);
    }

    private void validateBook(Long id, String name, String isbn, Integer pages, Integer year) throws BookValidationException {
        if (isbn == null || StringUtils.isBlank(isbn)) {
            throw new BookValidationException("ISBN cannot be null or empty");
        }
        if (!BookService.isValidIsbn13(isbn)) {
            throw new BookValidationException("Invalid ISBN");
        }
        if (name == null || StringUtils.isBlank(name)) {
            throw new BookValidationException("Book name cannot be null or empty");
        }
        if (pages == null || pages <= 0) {
            throw new BookValidationException("The book must contains at least one page");
        }
        if (year == null || year > Year.now().getValue()) {
            throw new BookValidationException("The publication year cannot be later than the current year");
        }
        BookEntity bookWithSameIsbn = repository.findByIsbn(isbn);
        if (bookWithSameIsbn != null && !bookWithSameIsbn.getId().equals(id)) {
            throw new BookValidationException("This ISBN is already used by another book");
        }
    }

    private void validateRating(Integer rating) throws InvalidRatingException {
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new InvalidRatingException("The rating must be between 1 and 5 stars");
        }
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
