package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.library.services.Formatter;

import java.util.StringJoiner;

@Data
@AllArgsConstructor
public class Author implements Formatter {
    private long id;
    private String name;
    private String surname;
    private String middleName;

    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String getShortInfo() {
        StringJoiner joiner = new StringJoiner(" ");
        String shortMiddleName = middleName.length() != 0 ? middleName.charAt(0) + "." : "";
        joiner.add(name);
        joiner.add(surname);
        joiner.add(shortMiddleName);
        return joiner.toString();
    }

}
