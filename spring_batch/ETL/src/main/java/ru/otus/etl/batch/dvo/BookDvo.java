package ru.otus.etl.batch.dvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.etl.database.relational.entity.AuthorEntity;
import ru.otus.etl.database.relational.entity.BookEntity;
import ru.otus.etl.database.relational.entity.CommentEntity;
import ru.otus.etl.database.relational.entity.GenreEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDvo {
    private BookEntity bookEntity;
    private List<AuthorEntity> authorEntities;
    private List<GenreEntity> genreEntities;
    private List<CommentEntity> commentEntities;
}
