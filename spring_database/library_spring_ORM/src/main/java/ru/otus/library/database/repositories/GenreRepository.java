package ru.otus.library.database.repositories;

import ru.otus.library.database.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findByIds(List<Long> id);

    List<Genre> findAll();

    void update(Genre updatedGenre);

    void delete(Genre genre);
}
