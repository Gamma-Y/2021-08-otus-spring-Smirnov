package ru.otus.etl.database.relational.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table(name = "authors")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<BookEntity> books = new ArrayList<>();

    public AuthorEntity(String fullName) {
        this.fullName = fullName;
    }

    public AuthorEntity(String fullName, List<BookEntity> books) {
        this.fullName = fullName;
        this.books = books;
    }

    public void addBook(BookEntity b) {
        this.books.add(b);
        b.getAuthors().add(this);
    }

    public void removeBook(BookEntity b) {
        this.books.remove(b);
        b.getAuthors().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity entity = (AuthorEntity) o;
        return Objects.equals(fullName, entity.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }
}
