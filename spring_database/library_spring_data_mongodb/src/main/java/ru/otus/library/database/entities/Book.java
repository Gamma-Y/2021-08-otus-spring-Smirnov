package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private ObjectId id;
    private String name;
    private List<String> generis = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    @DBRef
    private List<Author> authors = new ArrayList<>();

    public Book(String name, List<String> generis, List<Author> authors) {
        this.name = name;
        this.generis = generis;
        this.authors = authors;
    }

    public Book(String name, List<String> generis, List<Comment> comments, List<Author> authors) {
        this.name = name;
        this.generis = generis;
        this.comments = comments;
        this.authors = authors;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
    }
}
