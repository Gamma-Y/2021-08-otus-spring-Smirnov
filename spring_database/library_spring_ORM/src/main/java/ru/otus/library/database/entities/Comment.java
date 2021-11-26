package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.services.Formatter;

import javax.persistence.*;

@Data
@Table(name = "comments")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Formatter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_time")
    private long dateTime;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", nullable = false)
    private Book book;

    public Comment(String text, long dateTime, Book book) {
        this.text = text;
        this.dateTime = dateTime;
    }

    @Override
    public String getFullInfo() {
        return null;
    }

    @Override
    public String getShortInfo() {

        return text;
    }
}
