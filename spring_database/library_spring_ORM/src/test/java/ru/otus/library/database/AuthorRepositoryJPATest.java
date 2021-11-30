package ru.otus.library.database;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.database.entities.Author;


import javax.persistence.Query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("тест репозитория работающего с авторами")
@DataJpaTest
@Import(AuthorRepositoryJPA.class)
public class AuthorRepositoryJPATest {
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final long AUTHOR_ID = 1;
    private static final String AUTHOR_FULL_NAME = "test";
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
    }

    @DisplayName("должен найти всех авторов")
    @Test
    public void shouldFindAllExpectedAuthor() {
       List<Author> actual = repository.findAll();
       assertThat(actual).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("должен сохранить нового автора")
    @Test
    public void shouldSaveNewAuthor() {
        Author author = new Author(AUTHOR_FULL_NAME);
        repository.save(author);
        Author actual = em.find(Author.class, 3l);
        assertThat(actual.getFullName()).isNotNull().isEqualTo(AUTHOR_FULL_NAME);
    }

    @DisplayName("должен удалить автора")
    @Test
    public void shouldRemoveAuthor() {
        Author author = new Author(AUTHOR_FULL_NAME);
        author = em.persist(author);
        repository.delete(author);
        Author actual = em.find(Author.class, author.getId());
        assertThat(actual).isEqualTo(null);
    }

    @DisplayName("должен найти авторов по списку id")
    @Test
    public void shouldFindAuthorsByIDsList() {
        List<Long> ids = new ArrayList<>(){{add(1l); add(2l);}};
        List<Author> actual = repository.findByIds(ids);
        assertThat(actual).isNotNull().hasSize(2);
    }
}
