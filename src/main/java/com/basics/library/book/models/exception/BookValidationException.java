package com.basics.library.book.models.exception;

public class BookValidationException extends RuntimeException {

    public BookValidationException(String message) {
        super(message);
    }
}
