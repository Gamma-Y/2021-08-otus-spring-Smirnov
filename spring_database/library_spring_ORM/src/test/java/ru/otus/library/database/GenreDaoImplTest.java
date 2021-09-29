package ru.otus.library.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.library.database.entities.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тест DAO для жанров")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {
    private static final int EXPECTED_GENRE_COUNT = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "name";


    @Autowired
    private GenreDaoImpl dao;

    @Test
    @DisplayName("Должен вернуть не пустой список жанров")
    public void shouldReturnNotEmptyList() {
        List<Genre> actual = dao.getAll();
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Должен вернуть список содержащий один жанр")
    public void shouldReturnListWithOneGenre() {
        List<Genre> actual = dao.getAll();
        assertEquals(EXPECTED_GENRE_COUNT, actual.size());
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expected = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actual = dao.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expected = new Genre(2, "insert");
        dao.insert(expected);
        Genre actual = dao.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("выдает ошибку при попытке удаления заданного жанра по его id")
    @Test
    void shouldThrowExceptionDeleteGenreById() {
        assertThatThrownBy(() -> dao.deleteById(EXISTING_GENRE_ID))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
