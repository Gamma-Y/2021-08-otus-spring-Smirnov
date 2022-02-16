package ru.otus.etl.database.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class BookDocument {
    @Id
    private ObjectId id;
    private String name;
    private List<String> generis = new ArrayList<>();
    private List<CommentDocument> comments = new ArrayList<>();

    @DBRef
    private List<AuthorDocument> authors = new ArrayList<>();

    public BookDocument(String name, List<String> generis, List<AuthorDocument> authors) {
        this.name = name;
        this.generis = generis;
        this.authors = authors;
    }

    public BookDocument(String name, List<String> generis, List<CommentDocument> comments, List<AuthorDocument> authors) {
        this.name = name;
        this.generis = generis;
        this.comments = comments;
        this.authors = authors;
    }
}
