package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.dao.GenreDao;
import ru.otus.library.database.entities.Genre;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class GenreService {
    private final GenreDao genreDao;
    private final FormatterService formatter;

    @ShellMethod(key = "generis", value = "get all generis")
    public String getAll() {
        List<Genre> generis = genreDao.getAll();
        return formatter.formatList(generis);
    }

    @ShellMethod(key = "genre", value = "get genre by id")
    public String getById(long id) {
        return genreDao.getById(id).toString();
    }

    @ShellMethod(key = "new-genre", value = "insert new book (id, name)")
    public void insert(long id, String name) {
        Genre genre = new Genre(id, name);
        genreDao.insert(genre);
    }

    @ShellMethod(key = "delete-genre", value = "delete genre by id")
    public void delete(long id) {
        genreDao.deleteById(id);
    }
}
