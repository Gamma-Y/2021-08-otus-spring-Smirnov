package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.library.services.Formatter;

import java.util.StringJoiner;

@Data
@AllArgsConstructor
public class Book implements Formatter {
    private long id;
    private String name;
    private Author author;
    private Genre genre;

    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String getShortInfo() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name);
        joiner.add("Автор:" + author.getShortInfo());
        joiner.add("Жанр:" + genre.getShortInfo());
        return joiner.toString();
    }
}
