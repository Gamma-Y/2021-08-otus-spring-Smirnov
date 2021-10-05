package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.services.Formatter;

import javax.persistence.*;
import java.util.StringJoiner;

@Data
@Table(name = "authors")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Formatter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "middle_name")
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
