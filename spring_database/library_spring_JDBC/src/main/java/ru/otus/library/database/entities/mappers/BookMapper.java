package ru.otus.library.database.entities.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("books.id");
        String name = rs.getString("books.name");
        long genreId = rs.getLong("books.genre_id");
        long authorId = rs.getLong("books.author_id");
        String authorName = rs.getString("authors.name");
        String authorSurname = rs.getString("authors.surname");
        String authorMiddleName = rs.getString("authors.middlename");
        String genreName = rs.getString("generis.name");
        return new Book(id, name, new Author(authorId, authorName, authorSurname, authorMiddleName), new Genre(genreId, genreName));
    }
}
