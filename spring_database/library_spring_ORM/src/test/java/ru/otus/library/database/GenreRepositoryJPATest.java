package ru.otus.library.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.database.entities.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("тест репозитория работающего с жанрами")
@DataJpaTest
@Import(GenreRepositoryJPA.class)
public class GenreRepositoryJPATest {
    private static final int EXPECTED_NUMBER_OF_GENRES = 1;
    private static final long GENRE_ID = 1;
    private static final String GENRE_TITLE = "test";
    @Autowired
    private GenreRepositoryJPA repository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен найти жанр по id")
    @Test
    public void shouldFindExpectedGenreById() {
        Genre actual = repository.findById(GENRE_ID).get();
        Genre expected = em.find(Genre.class, GENRE_ID);
        assertEquals(expected, actual);
    }

    @DisplayName("должен найти все жанры")
    @Test
    public void shouldFindAllExpectedGenres() {
        List<Genre> actual = repository.findAll();
        assertThat(actual).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("должен сохранить новый жанр")
    @Test
    public void shouldSaveNewGenre() {
        Genre genre = new Genre(GENRE_TITLE);
        repository.save(genre);
        Genre actual = em.find(Genre.class, 2l);
        assertThat(actual.getTitle()).isNotNull().isEqualTo(GENRE_TITLE);
    }

    @DisplayName("должен удалить жанр")
    @Test
    public void shouldRemoveGenre() {
        Genre genre = new Genre(GENRE_TITLE);
        genre = em.persist(genre);
        repository.deleteById(genre.getId());
        em.flush();
        List<Genre> actual = em.getEntityManager().createQuery("select g from Genre g", Genre.class).getResultList();
        assertThat(actual).isNotNull().hasSize(1);
    }

    @DisplayName("должен найти жанры по списку id")
    @Test
    public void shouldFindGenresByIDsList() {
        List<Long> ids = new ArrayList<>(){{add(1l); add(2l);}};
        List<Genre> actual = repository.findById(ids);
        assertThat(actual).isNotNull().hasSize(1);
    }

}
