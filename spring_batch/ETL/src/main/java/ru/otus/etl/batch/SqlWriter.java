package ru.otus.etl.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.etl.batch.dvo.BookDvo;
import ru.otus.etl.database.relational.entity.AuthorEntity;
import ru.otus.etl.database.relational.entity.BookEntity;
import ru.otus.etl.database.relational.entity.CommentEntity;
import ru.otus.etl.database.relational.entity.GenreEntity;
import ru.otus.etl.database.relational.repositories.AuthorSqlRepository;
import ru.otus.etl.database.relational.repositories.BookSqlRepository;
import ru.otus.etl.database.relational.repositories.CommentSqlRepository;
import ru.otus.etl.database.relational.repositories.GenreSqlRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class SqlWriter implements ItemWriter<BookDvo> {

    private final BookSqlRepository bookSqlRepository;
    private final AuthorSqlRepository authorSqlRepository;
    private final GenreSqlRepository genreSqlRepository;
    private final CommentSqlRepository commentSqlRepository;

    @Override
    public void write(List<? extends BookDvo> list) {
        for (BookDvo bookDvo : list) {
            BookEntity book = bookSqlRepository.save(bookDvo.getBookEntity());
            List<CommentEntity> commentEntities = bookDvo.getCommentEntities();
            for (CommentEntity comment : commentEntities) {
                comment.setBook(book);
            }
            commentSqlRepository.saveAll(commentEntities);

            List<AuthorEntity> authorEntities = bookDvo.getAuthorEntities();
            for (AuthorEntity author : authorEntities) {
                AuthorEntity entity = authorSqlRepository.findByFullName(author.getFullName());
                if (entity == null) entity = new AuthorEntity(author.getFullName());
                entity.addBook(book);
                authorSqlRepository.save(entity);
            }

            List<GenreEntity> genreEntities = bookDvo.getGenreEntities();
            for (GenreEntity genre : genreEntities) {
                GenreEntity entity = genreSqlRepository.findByTitle(genre.getTitle());
                if (entity == null) entity = new GenreEntity(genre.getTitle());
                entity.addBook(book);
                genreSqlRepository.save(entity);
            }

        }


    }
}
