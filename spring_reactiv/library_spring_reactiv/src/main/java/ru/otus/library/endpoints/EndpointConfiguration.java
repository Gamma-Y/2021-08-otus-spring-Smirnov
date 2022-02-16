package ru.otus.library.endpoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.BookRepository;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class EndpointConfiguration {

    @Bean
    public RouterFunction<ServerResponse> bookRoute(BookRepository repository) {
        return route()
                .GET("/books",
                        r -> ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class)
                )
                .GET("/book/{id}",
                        r -> repository.findById(r.pathVariable("id"))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                                .switchIfEmpty(notFound().build())
                )
                .GET("/books/title",
                        r -> ok().contentType(APPLICATION_JSON)
                                .body(repository.findAll()
                                        .collectMap(Book::getId, Book::getName), Map.class)
                )


                .build();
    }

}
