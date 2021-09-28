package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.library.services.Formatter;

@Data
@AllArgsConstructor
public class Genre implements Formatter {
    private long id;
    private String name;

    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String getShortInfo() {
        return name;
    }
}
