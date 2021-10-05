package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.library.services.Formatter;

import javax.persistence.*;
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

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinTable(name = "books_generis",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> generis;

    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String getShortInfo() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name);
        joiner.add("Автор:");
        for (Author author : authors) {
            joiner.add(author.getShortInfo());
        }
        joiner.add("\n Жанр:");
        for (Genre genre : generis) {
            joiner.add(genre.getShortInfo());
        }
        joiner.add("\nКомментарии:\n\t");
        for (Comment comment : comments) {
            joiner.add(comment.getShortInfo());
            joiner.add("\n\t");
        }
        return joiner.toString();
    }
}
