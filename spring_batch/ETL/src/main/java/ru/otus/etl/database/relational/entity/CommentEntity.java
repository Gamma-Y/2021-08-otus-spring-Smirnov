package ru.otus.etl.database.relational.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Table(name = "comments")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_time")
    private long dateTime;

    @ManyToOne(targetEntity = BookEntity.class)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    public CommentEntity(String text, long dateTime) {
        this.text = text;
        this.dateTime = dateTime;
    }

    public CommentEntity(long id, String text, long dateTime) {
        this.id = id;
        this.text = text;
        this.dateTime = dateTime;
    }

    public CommentEntity(String text, long dateTime, BookEntity book) {
        this.text = text;
        this.dateTime = dateTime;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return dateTime == that.dateTime && Objects.equals(text, that.text) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, dateTime, book);
    }
}
