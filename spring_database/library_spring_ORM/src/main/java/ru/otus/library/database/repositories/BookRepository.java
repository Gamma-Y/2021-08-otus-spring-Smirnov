package ru.otus.library.database.repositories;

import ru.otus.library.database.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void updateId(Book book);

    void deleteById(long id);
}
