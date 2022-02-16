package ru.otus.etl.database.mongo.migration;

import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.*;
import ru.otus.etl.database.mongo.model.AuthorDocument;
import ru.otus.etl.database.mongo.model.BookDocument;
import ru.otus.etl.database.mongo.model.CommentDocument;
import ru.otus.etl.database.mongo.repositories.AuthorNoSqlRepository;
import ru.otus.etl.database.mongo.repositories.BookNoSqlRepository;
import ru.otus.etl.service.mongo.SequenceService;

import java.util.List;

@ChangeUnit(id = "migration", order = "01", runAlways = true)
public class Migrate {

    @BeforeExecution
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
    }

    @Execution
    public void addData(SequenceService sequence, BookNoSqlRepository bookRepository, AuthorNoSqlRepository authorRepository) {
        AuthorDocument author1 = authorRepository.insert(new AuthorDocument("Кэтти Сьерра"));
        AuthorDocument author2 = authorRepository.insert(new AuthorDocument("Роберт Мартин Сесил"));
        AuthorDocument author3 = authorRepository.insert(new AuthorDocument("Клейсон Джорж Самюэль"));

        CommentDocument comment1 = new CommentDocument(sequence.generateSequence(CommentDocument.SEQUENCE_NAME), "comment1", System.currentTimeMillis());
        CommentDocument comment2 = new CommentDocument(sequence.generateSequence(CommentDocument.SEQUENCE_NAME), "comment2", System.currentTimeMillis());
        CommentDocument comment3 = new CommentDocument(sequence.generateSequence(CommentDocument.SEQUENCE_NAME), "comment3", System.currentTimeMillis());
        CommentDocument comment4 = new CommentDocument(sequence.generateSequence(CommentDocument.SEQUENCE_NAME), "comment4", System.currentTimeMillis());
        CommentDocument comment5 = new CommentDocument(sequence.generateSequence(CommentDocument.SEQUENCE_NAME), "comment5", System.currentTimeMillis());
        CommentDocument comment6 = new CommentDocument(sequence.generateSequence(CommentDocument.SEQUENCE_NAME), "comment6", System.currentTimeMillis());

        BookDocument book = bookRepository.insert(new BookDocument("Head First Java, Изучаем Java",
                List.of("Программирование"),
                List.of(comment1),
                List.of(author1, author2)));
        BookDocument book2 = bookRepository.insert(new BookDocument("Чистый код. Создание, анализ и рефакторинг",
                List.of("Программирование"),
                List.of(comment2, comment3),
                List.of(author2)));
        BookDocument book3 = bookRepository.insert(new BookDocument("Самый богатый человек в Вавилоне",
                List.of("Финансы"),
                List.of(comment4, comment5, comment6),
                List.of(author1, author3)));
    }

    @RollbackExecution
    public void rollback() {
    }
}
