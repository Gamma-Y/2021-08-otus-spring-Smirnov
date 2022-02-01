package ru.otus.library.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.library.controllers.dto.AuthorDTO;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.controllers.dto.CommentDTO;
import ru.otus.library.controllers.dto.GenreDTO;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;

import java.util.List;

@WebFluxTest(controllers = BookController.class)
public class BookControllerTest {
    private static final Book BOOK = new Book("1", "name",
            List.of("genre"),
            List.of(new Comment(System.currentTimeMillis(), "text")),
            List.of(new Author("fullName")));
    @MockBean
    private BookRepository repository;
    @Autowired
    private WebTestClient client;


    @Test
    public void shouldDeleteBookById() {
        Mockito.when(repository.deleteById(Mockito.anyString())).thenReturn(Mono.empty());
        client.get()
                .uri("/delete/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$", true);

        Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyString());
    }

    @Test
    public void shouldAddNewGenreToBook() {
        GenreDTO genreDTO = new GenreDTO("123", "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/book/add/genre")
                .bodyValue(genreDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldAddNewAuthorToBook() {
        AuthorDTO authorDTO = new AuthorDTO("23123", "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/book/add/author")
                .bodyValue(authorDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldAddNewCommentToBook() {
        CommentDTO commentDTO = new CommentDTO("1", System.currentTimeMillis(), "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/book/add/comment")
                .bodyValue(commentDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldUpdateBookName() {
        BookDTO bookDTO = new BookDTO("1", "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/update/book")
                .bodyValue(bookDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldDeleteAuthorFromBook() {
        AuthorDTO authorDTO = new AuthorDTO("23123", "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/author/delete")
                .bodyValue(authorDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldDeleteGenreFromBook() {
        GenreDTO genreDTO = new GenreDTO("123", "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/genre/delete")
                .bodyValue(genreDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldDeleteCommentFromBook() {
        CommentDTO commentDTO = new CommentDTO("1", System.currentTimeMillis(), "test");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));

        client.post()
                .uri("/comment/delete")
                .bodyValue(commentDTO)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }
}
