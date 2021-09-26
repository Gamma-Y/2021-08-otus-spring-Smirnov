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

    @ShellMethod(key = "author", value = "get author by id")
    public String getById(long id) {
        return authorDao.getById(id).toString();
    }

    @ShellMethod(key = "new-author", value = "insert new author (id, name, surname, middleName)")
    public void insert(long id, String name, String surname, String middleName) {
        Author author = new Author(id, name, surname, middleName);
        authorDao.insert(author);
    }

    @ShellMethod(key = "delete-author", value = "delete author by id")
    public void delete(long id) {
        authorDao.deleteById(id);
    }

}
