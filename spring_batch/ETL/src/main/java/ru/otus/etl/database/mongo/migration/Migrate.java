package ru.otus.etl.database.mongo.migration;

import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.*;
import ru.otus.etl.database.mongo.model.Author;
import ru.otus.etl.database.mongo.model.Book;
import ru.otus.etl.database.mongo.model.Comment;
import ru.otus.etl.database.mongo.repositories.AuthorRepository;
import ru.otus.etl.database.mongo.repositories.BookRepository;
import ru.otus.etl.service.mongo.SequenceService;

import java.util.List;

@ChangeUnit(id = "migration", order = "1", runAlways = true)
public class Migrate {

    @BeforeExecution
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
    }

    @Execution
    public void addData(SequenceService sequence, BookRepository bookRepository, AuthorRepository authorRepository) {
        Author author1 = authorRepository.insert(new Author("Кэтти Сьерра"));
        Author author2 = authorRepository.insert(new Author("Роберт Мартин Сесил"));
        Author author3 = authorRepository.insert(new Author("Клейсон Джорж Самюэль"));

        Comment comment1 = new Comment(sequence.generateSequence(Comment.SEQUENCE_NAME), "comment1", System.currentTimeMillis());
        Comment comment2 = new Comment(sequence.generateSequence(Comment.SEQUENCE_NAME), "comment1", System.currentTimeMillis());
        Comment comment3 = new Comment(sequence.generateSequence(Comment.SEQUENCE_NAME), "comment1", System.currentTimeMillis());
        Comment comment4 = new Comment(sequence.generateSequence(Comment.SEQUENCE_NAME), "comment1", System.currentTimeMillis());
        Comment comment5 = new Comment(sequence.generateSequence(Comment.SEQUENCE_NAME), "comment1", System.currentTimeMillis());
        Comment comment6 = new Comment(sequence.generateSequence(Comment.SEQUENCE_NAME), "comment1", System.currentTimeMillis());

        Book book = bookRepository.insert(new Book("Head First Java, Изучаем Java",
                List.of("Программирование"),
                List.of(comment1, comment2),
                List.of(author1, author2)));
        Book book2 = bookRepository.insert(new Book("Чистый код. Создание, анализ и рефакторинг",
                List.of("Программирование"),
                List.of(comment3),
                List.of(author2)));
        Book book3 = bookRepository.insert(new Book("Самый богатый человек в Вавилоне",
                List.of("Финансы"),
                List.of(comment4, comment5, comment6),
                List.of(author1, author3)));
    }

    @RollbackExecution
    public void rollback() {
    }
}
