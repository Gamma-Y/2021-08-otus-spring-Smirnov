package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.library.services.Formatter;

import javax.persistence.*;

@Data
@Table(name = "comments")
@Entity
@AllArgsConstructor
public class Comment implements Formatter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    private String text;

    @Override
    public String getFullInfo() {
        return null;
    }

    @Override
    public String getShortInfo() {
        return null;
    }
}
