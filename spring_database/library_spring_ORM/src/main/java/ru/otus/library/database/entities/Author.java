package ru.otus.library.database.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.services.Formatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "authors")
@Entity
@NoArgsConstructor
public class Author implements Formatter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public void addBook(Book b) {
        this.books.add(b);
        b.getAuthors().add(this);
    }

    public void removeBook(Book b) {
        this.books.remove(b);
        b.getAuthors().remove(this);
    }
    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String getShortInfo() {
        return fullName;
    }

}
