package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.dao.BookDao;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.mappers.BookMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select id, name, genre_id, author_id from books where id=:id", params, new BookMapper());
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into books (id, `name`, `genre_id`, `author_id`) values (:id, :name, :genre_id, :author_id)",
                Map.of("id", book.getId(), "name", book.getName(), "genre_id", book.getGenreId(), "author_id", book.getAuthorId()));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
    }
}
