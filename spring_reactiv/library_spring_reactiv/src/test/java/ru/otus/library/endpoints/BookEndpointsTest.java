package ru.otus.library.endpoints;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class BookEndpointsTest {
    private static final Book BOOK = new Book("1", "name",
            List.of("genre"),
            List.of(new Comment(System.currentTimeMillis(), "text")),
            List.of(new Author("fullName")));
    @Autowired
    private RouterFunction<ServerResponse> route;
    private WebTestClient client;
    @MockBean
    private BookRepository repository;

    @Test
    public void shouldReturnAllBooks() {
        client = WebTestClient
                .bindToRouterFunction(route)
                .build();
        Flux<Book> flux = Flux.just(BOOK);
        Mockito.when(repository.findAll()).thenReturn(flux);
        client.get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$", BOOK);

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnBookById() {
        client = WebTestClient
                .bindToRouterFunction(route)
                .build();
        Mono<Book> mono = Mono.just(BOOK);
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(mono);
        client.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$", BOOK);

        Mockito.verify(repository, Mockito.times(1)).findById(BOOK.getId());
    }

    @Test
    public void shouldReturnNotFound() {
        client = WebTestClient
                .bindToRouterFunction(route)
                .build();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.empty());
        client.get()
                .uri("/book/5")
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    public void shouldReturnMapIdAndTitle() {
        client = WebTestClient
                .bindToRouterFunction(route)
                .build();
        Mockito.when(repository.findAll()).thenReturn(Flux.just(BOOK));
        client.get()
                .uri("/books/title")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class);

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }


}
