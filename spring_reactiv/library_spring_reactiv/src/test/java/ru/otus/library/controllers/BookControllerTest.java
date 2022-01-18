package ru.otus.library.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.library.controllers.dto.AuthorDTO;
import ru.otus.library.controllers.dto.GenreDTO;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;

import java.util.List;

@WebFluxTest
@AutoConfigureWebTestClient
public class BookControllerTest {
    private static final Book BOOK = new Book("1", "name",
            List.of("genre"),
            List.of(new Comment(System.currentTimeMillis(), "text")),
            List.of(new Author("fullName")));
    @Autowired
    private WebTestClient client;
    @MockBean
    private BookRepository repository;

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
        Mono<GenreDTO> genreDTOMono = Mono.just(genreDTO);
        Mockito.when(repository.findById(genreDTO.getBookId())).thenReturn(Mono.just(BOOK));
        client.post()
                .uri("/book/add/genre")
                .body(genreDTOMono, GenreDTO.class)
                .exchange()
                .expectStatus().isOk();


        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldAddNewAuthorToBook() {
        AuthorDTO authorDTO = new AuthorDTO("23123", "test");
        Mono<AuthorDTO> authorDTOMono = Mono.just(authorDTO);
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(BOOK));
        client.post()
                .uri("/book/add/author")
                .body(authorDTOMono, AuthorDTO.class)
                .exchange()
                .expectStatus().isOk();
//                .expectBody()
//                .jsonPath("$.generis[1]").isEqualTo(genreDTO.getTitle());

//        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }
}
