package com.basics.library.book.dto;

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

        @NotNull
        @NotBlank
        String isbn;

        @NotNull
        Integer pages;

        @NotNull
        Integer year;

        String description;

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
        String isbn;
        Integer pages;
        Integer year;
        String description;
        Integer rating;
    }
}
