package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.dao.GenreDao;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.entities.mappers.AuthorMapper;
import ru.otus.library.database.entities.mappers.GenreMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;


    @Override
    public List<Genre> getAll() {
        return jdbc.query("select `id`,`name` from generis", new GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        return null;
    }

    @Override
    public void update(Genre genre) {

    }

    @Override
    public void deleteById(long id) {

    }
}
