package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.dao.BookDao;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.mappers.BookMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Book> getAll() {

        return jdbc.query("select id, name, genre_id, author_id from books", new BookMapper());
    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void deleteById(long id) {

    }
}
