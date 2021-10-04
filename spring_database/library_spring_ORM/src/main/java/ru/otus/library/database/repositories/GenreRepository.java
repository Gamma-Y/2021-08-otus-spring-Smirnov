package ru.otus.library.database.repositories;

import ru.otus.library.database.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    void updateNameById(long id, String title);

    void deleteById(long id);
}
