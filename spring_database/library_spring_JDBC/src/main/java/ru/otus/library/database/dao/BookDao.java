package ru.otus.library.database.dao;

import ru.otus.library.database.entities.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();

    Book getById(long id);

    void update(Book book);

    void deleteById(long id);
}
