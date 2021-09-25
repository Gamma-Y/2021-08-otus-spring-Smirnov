package ru.otus.library.database.entities.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.library.database.entities.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        long genreId = rs.getLong("genre_id");
        long authorId = rs.getLong("author_id");
        return new Book(id, name, genreId, authorId);
    }
}
