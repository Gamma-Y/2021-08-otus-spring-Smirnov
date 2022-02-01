package ru.otus.etl.database.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";
    @Id
    private Long id;
    private String text;
    private long dateTime;
}
