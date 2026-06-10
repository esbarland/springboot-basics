package com.basics.library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class BookDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {
        String name;
        Integer pages;
    }
}
