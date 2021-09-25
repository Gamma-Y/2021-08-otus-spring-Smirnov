package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.dao.AuthorDao;
import ru.otus.library.database.entities.Author;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AuthorService {
    private final AuthorDao authorDao;
    private final FormatterService formatter;

    @ShellMethod(key = "authors", value = "get all authors")
    public String getAll() {
        List<Author> authors = authorDao.getAll();
        return formatter.formatList(authors);
    }

}
