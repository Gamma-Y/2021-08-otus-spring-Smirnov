package ru.otus.library.controllers.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String bookId;
    private long timestamp;
    private String text;

}
