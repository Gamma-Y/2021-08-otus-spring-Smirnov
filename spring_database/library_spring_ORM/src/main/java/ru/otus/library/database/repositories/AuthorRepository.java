package ru.otus.library.database.repositories;

import ru.otus.library.database.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    void updateNameById(long id, String name);

    void deleteById(long id);
}
