package ru.otus.etl.database.relational.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table(name = "books")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = CommentEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<CommentEntity> comments = new ArrayList<>();

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = AuthorEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<AuthorEntity> authors = new ArrayList<>();

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = GenreEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "books_generis",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> generis = new ArrayList<>();

    public BookEntity(String name, List<AuthorEntity> authors, List<GenreEntity> generis) {
        this.name = name;
        this.authors = authors;
        this.generis = generis;
    }

    public BookEntity(String name, List<CommentEntity> comments, List<AuthorEntity> authors, List<GenreEntity> generis) {
        this.name = name;
        this.comments = comments;
        this.authors = authors;
        this.generis = generis;
    }

    public void addComment(CommentEntity comment) {
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(CommentEntity comment) {
        comments.remove(comment);
        comment.setBook(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity book = (BookEntity) o;
        return Objects.equals(name, book.name) && Objects.equals(comments, book.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, comments);
    }
}
