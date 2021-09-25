package ru.otus.library.database.entities.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.library.database.entities.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String middleName = rs.getString("middleName");
        return new Author(1, name, surname, middleName);
    }
}
