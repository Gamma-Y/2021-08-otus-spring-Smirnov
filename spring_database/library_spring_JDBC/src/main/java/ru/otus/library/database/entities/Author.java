package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Author {
    private long id;
    private String name;
    private String surname;
    private String middleName;
}
