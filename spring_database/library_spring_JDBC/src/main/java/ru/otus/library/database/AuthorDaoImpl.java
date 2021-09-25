package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.dao.AuthorDao;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.mappers.AuthorMapper;

import java.util.List;

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
        return null;
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public void deleteById(long id) {

    }
}
