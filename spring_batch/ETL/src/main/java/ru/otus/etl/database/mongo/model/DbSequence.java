package ru.otus.etl.database.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "db_sequence")
public class DbSequence {
    @Id
    private String id;
    private Long count;
}
