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
}
