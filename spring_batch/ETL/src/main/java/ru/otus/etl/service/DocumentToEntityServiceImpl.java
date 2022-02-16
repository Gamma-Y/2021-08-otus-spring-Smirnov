package ru.otus.etl.service;

import org.springframework.stereotype.Service;
import ru.otus.etl.batch.dvo.BookDvo;
import ru.otus.etl.database.mongo.model.AuthorDocument;
import ru.otus.etl.database.mongo.model.BookDocument;
import ru.otus.etl.database.mongo.model.CommentDocument;
import ru.otus.etl.database.relational.entity.AuthorEntity;
import ru.otus.etl.database.relational.entity.BookEntity;
import ru.otus.etl.database.relational.entity.CommentEntity;
import ru.otus.etl.database.relational.entity.GenreEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentToEntityServiceImpl implements DocumentToEntityService, DvoService {

    @Override
    public BookDvo createBookDvo(BookDocument document) {
        BookDvo bookDvo = new BookDvo();
        bookDvo.setBookEntity(bookDocumentToEntity(document));
        bookDvo.setAuthorEntities(getAuthorEntityFromDocument(document.getAuthors()));
        bookDvo.setCommentEntities(getCommentEntityFromDocument(document.getComments()));
        bookDvo.setGenreEntities(getGenreEntityFromDocument(document.getGeneris()));
        return bookDvo;
    }

    @Override
    public BookEntity bookDocumentToEntity(BookDocument document) {
        BookEntity book = new BookEntity();
        book.setName(document.getName());
        return book;
    }

    @Override
    public GenreEntity genreDocumentToEntity(String document) {
        return new GenreEntity(document);
    }

    @Override
    public AuthorEntity authorDocumentToEntity(AuthorDocument document) {
        return new AuthorEntity(document.getFullName());
    }

    @Override
    public CommentEntity commentDocumentToEntity(CommentDocument document) {
        return new CommentEntity(document.getText(), document.getDateTime());
    }

    private List<AuthorEntity> getAuthorEntityFromDocument(List<AuthorDocument> documents) {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        for (AuthorDocument author : documents) {
            AuthorEntity entity = authorDocumentToEntity(author);
            authorEntities.add(entity);
        }
        return authorEntities;
    }

    private List<GenreEntity> getGenreEntityFromDocument(List<String> genres) {
        List<GenreEntity> genreEntities = new ArrayList<>();
        for (String genre : genres) {
            GenreEntity entity = genreDocumentToEntity(genre);
            genreEntities.add(entity);
        }
        return genreEntities;
    }

    private List<CommentEntity> getCommentEntityFromDocument(List<CommentDocument> documents) {
        List<CommentEntity> commentEntities = new ArrayList<>();
        for (CommentDocument comment : documents) {
            CommentEntity entity = commentDocumentToEntity(comment);
            commentEntities.add(entity);
        }
        return commentEntities;
    }


}
