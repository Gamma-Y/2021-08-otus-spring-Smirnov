package ru.otus.etl.service;

import ru.otus.etl.database.mongo.model.AuthorDocument;
import ru.otus.etl.database.mongo.model.BookDocument;
import ru.otus.etl.database.mongo.model.CommentDocument;
import ru.otus.etl.database.relational.entity.AuthorEntity;
import ru.otus.etl.database.relational.entity.BookEntity;
import ru.otus.etl.database.relational.entity.CommentEntity;
import ru.otus.etl.database.relational.entity.GenreEntity;

public interface DocumentToEntityService {
    BookEntity bookDocumentToEntity(BookDocument document);

    GenreEntity genreDocumentToEntity(String document);

    AuthorEntity authorDocumentToEntity(AuthorDocument document);

    CommentEntity commentDocumentToEntity(CommentDocument document);
}
