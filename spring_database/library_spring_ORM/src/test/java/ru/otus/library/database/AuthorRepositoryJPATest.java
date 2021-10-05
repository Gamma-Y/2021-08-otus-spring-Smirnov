package ru.otus.library.database;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.database.entities.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("тест репозитория работающего с авторами")
@DataJpaTest
@Import(AuthorRepositoryJPA.class)
public class AuthorRepositoryJPATest {
    private static final long AUTHOR_ID = 1;
    @Autowired
    private AuthorRepositoryJPA repository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен найти автора по id")
    @Test
    public void shouldFindExpectedAuthorById() {
        Author actual = repository.findById(AUTHOR_ID).get();
        Author expected = em.find(Author.class, AUTHOR_ID);
        assertEquals(expected, actual);
//        assertThat(actual).isPresent().get()
//                .usingRecursiveComparison().isEqualTo(expected);
    }
}
