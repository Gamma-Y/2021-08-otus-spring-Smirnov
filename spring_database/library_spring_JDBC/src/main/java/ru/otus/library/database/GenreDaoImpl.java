package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.dao.GenreDao;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.entities.mappers.GenreMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select `id`,`name` from generis where id=:id", params, new GenreMapper());
    }

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into generis (id, `name`) values (:id, :name)",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from generis where id = :id", params);
    }
}
