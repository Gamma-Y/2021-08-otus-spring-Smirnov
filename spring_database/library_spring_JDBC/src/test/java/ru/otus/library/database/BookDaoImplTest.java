package ru.otus.library.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тест DAO для книг")
@JdbcTest
@Import(BookDaoImpl.class)
public class BookDaoImplTest {
    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_NAME = "name";
    private static final Genre EXISTING_BOOK_GENRE = new Genre(1, "name");
    private static final Author EXISTING_BOOK_AUTHOR = new Author(1, "name", "surname", "middlename");


    @Autowired
    private BookDaoImpl dao;

    @Test
    @DisplayName("Должен вернуть не пустой список книг")
    public void shouldReturnNotEmptyList() {
        List<Book> actual = dao.getAll();
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Должен вернуть список содержащий одину книгу")
    public void shouldReturnListWithOneBook() {
        List<Book> actual = dao.getAll();
        assertEquals(EXPECTED_BOOK_COUNT, actual.size());
    }

    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expected = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        Book actual = dao.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expected = new Book(2, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        dao.insert(expected);
        Book actual = dao.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("удаляет заданную книги по её id")
    @Test
    void shouldThrowExceptionDeleteBookById() {
        assertThatCode(() -> dao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        dao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> dao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
