package ru.otus.library.database.dao;

import ru.otus.library.database.entities.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();

    Author getById(long id);

    void update(Author author);

    void deleteById(long id);
}
