package ru.otus.library.database.healthcheck;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.library.database.repositories.AuthorRepository;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.Map;

@Component
@AllArgsConstructor
public class DBcheck implements HealthIndicator {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    @Override
    public Health health() {
        long authorCount = authorRepository.count();
        long bookCount = bookRepository.count();
        long commentCount = commentRepository.count();
        long genreCount = genreRepository.count();
        return Health.up()
                .withDetails(Map.of("bookCount", bookCount,
                        "authorCount", authorCount,
                        "commentCount", commentCount,
                        "genreCount", genreCount))
                .build();
    }
}
