package ru.otus.library.database.dao;

import ru.otus.library.database.entities.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    Genre getById(long id);

    void insert(Genre genre);

    void deleteById(long id);
}
