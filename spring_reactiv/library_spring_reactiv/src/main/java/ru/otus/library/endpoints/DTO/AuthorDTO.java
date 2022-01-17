package ru.otus.library.endpoints.DTO;

import lombok.Data;
import ru.otus.library.database.entities.Author;

@Data
public class AuthorDTO {
    private String bookId;
    private String author;
}
