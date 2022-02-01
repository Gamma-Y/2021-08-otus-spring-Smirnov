package ru.otus.etl.database.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private ObjectId id;
    @Field(value = "fullName")
    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
