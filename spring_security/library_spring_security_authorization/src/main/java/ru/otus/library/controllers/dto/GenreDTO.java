package ru.otus.library.controllers.dto;

import lombok.Data;

@Data
public class GenreDTO {
    private long id;
    private long bookId;
    private String title;
}
