package ru.otus.library.endpoints.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    private String bookId;
    private long timestamp;
    private String text;

}
