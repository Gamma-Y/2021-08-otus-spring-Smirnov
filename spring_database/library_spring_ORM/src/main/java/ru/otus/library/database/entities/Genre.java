package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.services.Formatter;

import javax.persistence.*;

@Data
@Table(name = "generis")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Formatter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String getShortInfo() {
        return title;
    }
}
