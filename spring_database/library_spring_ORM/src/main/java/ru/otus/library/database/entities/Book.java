package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Data
@Table(name = "books")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER)
    @JoinTable(name = "books_generis",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> generis = new ArrayList<>();

    public Book(String name, List<Author> authors, List<Genre> generis) {
        this.name = name;
        this.authors = authors;
        this.generis = generis;
    }

    public Book(String name, List<Comment> comments, List<Author> authors, List<Genre> generis) {
        this.name = name;
        this.comments = comments;
        this.authors = authors;
        this.generis = generis;
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setBook(null);
    }
}
