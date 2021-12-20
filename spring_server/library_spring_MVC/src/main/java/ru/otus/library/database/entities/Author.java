package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "authors")
@Entity
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Book> books = new ArrayList<>();

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public Author(long id, String fullName) {
        this.id = id;
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

}
