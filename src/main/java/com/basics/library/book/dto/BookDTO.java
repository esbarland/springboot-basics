package com.basics.library.book.dto;

import com.basics.library.book.models.BookCategory;
import com.basics.library.book.models.BookStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class BookDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {
        @NotNull
        @NotBlank
        String name;

        String author;

        @NotNull
        @NotBlank
        String isbn;

        @NotNull
        Integer pages;

        @NotNull
        Integer year;

        String description;

        BookCategory category;

        BookStatus status;

        @Min(1)
        @Max(5)
        Integer rating;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class RatingInput {
        @NotNull
        @Min(1)
        @Max(5)
        Integer rating;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostOutput {
        Long id;
        String name;
        String author;
        String isbn;
        Integer pages;
        Integer year;
        String description;
        BookCategory category;
        BookStatus status;
        Integer rating;
    }
}
