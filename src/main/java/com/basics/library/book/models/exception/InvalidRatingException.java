package com.basics.library.book.models.exception;

public class InvalidRatingException extends RuntimeException {

    public InvalidRatingException(String message) {
        super(message);
    }
}
