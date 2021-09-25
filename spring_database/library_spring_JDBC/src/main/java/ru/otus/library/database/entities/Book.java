package ru.otus.library.database.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Book {
    private long id;
    private String name;
    private long genreId;
    private long authorId;
}
