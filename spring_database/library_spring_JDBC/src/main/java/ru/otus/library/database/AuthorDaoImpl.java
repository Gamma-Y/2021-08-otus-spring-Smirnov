package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.dao.AuthorDao;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.mappers.AuthorMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;


    @Override
    public List<Author> getAll() {

        return jdbc.query("select `id`,`name`,`surname`, `middleName` from authors", new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select `id`,`name`,`surname`, `middleName` from authors where id=:id", params, new AuthorMapper());
    }

    @Override
    public void insert(Author author) {
        jdbc.update("insert into authors (id, `name`, `surname`, `middleName`) values (:id, :name, :surname, :middleName)",
                Map.of("id", author.getId(), "name", author.getName(), "surname", author.getSurname(), "middleName", author.getMiddleName()));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from authors where id = :id", params);
    }
}
