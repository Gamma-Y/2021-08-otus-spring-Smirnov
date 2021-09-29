package ru.otus.library.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.library.database.entities.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тест DAO для авторов")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {
    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "name";
    private static final String EXISTING_SURNAME_NAME = "surname";
    private static final String EXISTING_MIDDLENAME_NAME = "middlename";

    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    @DisplayName("Должен вернуть не пустой список авторов")
    public void shouldReturnNotEmptyList() {
        List<Author> actual = authorDao.getAll();
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Должен вернуть список содержащий одного автора")
    public void shouldReturnListWithOneAuthor() {
        List<Author> actual = authorDao.getAll();
        assertEquals(EXPECTED_AUTHOR_COUNT, actual.size());
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expected = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_SURNAME_NAME, EXISTING_MIDDLENAME_NAME);
        Author actual = authorDao.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expected = new Author(2, "insert", "insert", "insert");
        authorDao.insert(expected);
        Author actual = authorDao.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("выдает ошибку при попытке удаления заданного автора по его id")
    @Test
    void shouldThrowExceptionDeleteAuthorById() {
        assertThatThrownBy(() -> authorDao.deleteById(EXISTING_AUTHOR_ID))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
