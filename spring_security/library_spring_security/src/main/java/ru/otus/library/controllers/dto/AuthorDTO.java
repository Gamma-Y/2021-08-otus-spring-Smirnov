package ru.otus.library.controllers.dto;

import lombok.Data;

@Data
public class AuthorDTO {
    private long id;
    private long bookId;
    private String fullName;
}
