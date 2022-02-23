package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "generis")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ToString.Exclude
    @ManyToMany(mappedBy="generis")
    private List<Book> books = new ArrayList<Book>();

    public Genre(String title) {
        this.title = title;
    }

    public Genre(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addBook(Book b) {
        this.books.add(b);
        b.getGeneris().add(this);
    }

    public void removeBook(Book b) {
        this.books.remove(b);
        b.getGeneris().remove(this);
    }

}
