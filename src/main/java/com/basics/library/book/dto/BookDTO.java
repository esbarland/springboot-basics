package com.basics.library.book.dto;

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
        Integer pages;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostOutput {
        Long id;
        String name;
        Integer pages;
    }
}
