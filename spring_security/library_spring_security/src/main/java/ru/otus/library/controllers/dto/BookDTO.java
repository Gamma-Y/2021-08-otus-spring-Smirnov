package ru.otus.library.controllers.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    private long id;
    private String name;
    private List<String> authors;
    private List<String> genres;
}
