package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.library.services.Formatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Data
@Table(name = "books")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Formatter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments = new ArrayList<>();

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
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

    @Override
    public String getFullInfo() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name);
        joiner.add("\n Автор:");
        for (Author author : authors) {
            joiner.add(author.getShortInfo() + ";");
        }
        joiner.add("\n Жанр:");
        for (Genre genre : generis) {
            joiner.add(genre.getShortInfo() + ";");
        }
        joiner.add("\n Комментарии:\n\t");
        for (Comment comment : comments) {
            joiner.add(comment.getShortInfo() + ";");
            joiner.add("\n\t");
        }
        return joiner.toString();
    }

    @Override
    public String getShortInfo() {
       return name;
    }
}
