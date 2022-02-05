package ru.otus.library.controllers.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String text;
    private long id;
    private long bookId;
    private long timeStamp;
}
