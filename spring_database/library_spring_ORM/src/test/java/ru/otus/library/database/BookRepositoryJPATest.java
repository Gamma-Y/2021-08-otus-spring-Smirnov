package ru.otus.library.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("тест репозитория работающего с книгами")
@DataJpaTest
@Import(BookRepositoryJPA.class)
public class BookRepositoryJPATest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 1;
    private static final long BOOK_ID = 1;
    private static final String BOOK_TITLE = "test";

    @Autowired
    private BookRepositoryJPA repository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен найти кингу по id")
    @Test
    public void shouldFindExpectedBookById() {
        Book actual = repository.findById(BOOK_ID).get();
        Book expected = em.find(Book.class, BOOK_ID);
        assertEquals(expected, actual);
    }

    @DisplayName("должен найти все книги")
    @Test
    public void shouldFindAllExpectedBooks() {
        List<Book> actual = repository.findAll();
        assertThat(actual).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("должен сохранить новую книгу")
    @Test
    public void shouldSaveNewBook() {
        Author author = em.find(Author.class, 1l);
        Genre genre = em.find(Genre.class, 1l);
        Book book = new Book(BOOK_TITLE, Collections.singletonList(author), Collections.singletonList(genre));
        book = repository.save(book);
        Book actual = em.find(Book.class, book.getId());
        assertThat(actual).isNotNull().isEqualTo(book);
    }

    @DisplayName("должен удалить книгу")
    @Test
    public void shouldRemoveBook() {
        Book book = new Book(BOOK_TITLE, null, null);
        book = em.persistAndFlush(book);
        repository.delete(book);
        Book actual = em.find(Book.class, book.getId());
        assertThat(actual).isEqualTo(null);
    }

}
