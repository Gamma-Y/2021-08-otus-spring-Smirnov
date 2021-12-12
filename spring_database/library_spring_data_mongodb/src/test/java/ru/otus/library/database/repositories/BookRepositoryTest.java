package ru.otus.library.database.repositories;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {
    private static final String GENRE = "Программирование";
    private static final String DOESNT_EXIST_GENRE = "test";
    private static Author author = new Author("test");
    private static Book book = new Book("test",
            List.of(GENRE),
            List.of(new Comment(1L, "comment1", System.currentTimeMillis())),
            List.of(author));
    @Autowired
    private BookRepository repository;

    @BeforeAll
    public static void initDb(@Autowired MongoTemplate db) {
        author = db.insert(author);
        book = db.insert(book);
    }

    @AfterAll
    public static void dropDb(@Autowired MongoTemplate db) {
        db.getDb().drop();
    }

    @Test
    @DisplayName("должен найти все книги с заданным жанром")
    public void shouldFindBooksByGenre() {
        List<Book> actual = repository.findByGeneris(GENRE);
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("должен вернуть пустой список книг по заданному жанру")
    public void shouldReturnEmptyBooksByGenre() {
        List<Book> actual = repository.findByGeneris(DOESNT_EXIST_GENRE);
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("должен вернуть список книг по заданному автору ")
    public void shouldReturnBooksByAuthor() {
        List<Book> actual = repository.findByAuthors(author.getId());
        assertThat(actual).isEqualTo(Collections.singletonList(book));
    }

}
