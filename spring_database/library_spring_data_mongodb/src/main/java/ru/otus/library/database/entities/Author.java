package ru.otus.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private ObjectId id;
    @Field(value = "fullName")
    private String fullName;
//    @DBRef
//    @Field(value = "books")
//    private List<Book> books = new ArrayList<>();

    public Author(String fullName) {
        this.fullName = fullName;
    }

//    public void addBook(Book b) {
//        this.books.add(b);
//    }
//
//    public void removeBook(Book b) {
//        this.books.remove(b);
//    }

}
