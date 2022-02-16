package ru.otus.etl.database.relational.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Table(name = "generis")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ToString.Exclude
    @ManyToMany(mappedBy = "generis")
    private List<BookEntity> books = new ArrayList<>();

    public GenreEntity(String title) {
        this.title = title;
    }

    public GenreEntity(String title, List<BookEntity> books) {
        this.title = title;
        this.books = books;
    }

    public void addBook(BookEntity b) {
        this.books.add(b);
        b.getGeneris().add(this);
    }

    public void removeBook(BookEntity b) {
        this.books.remove(b);
        b.getGeneris().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
