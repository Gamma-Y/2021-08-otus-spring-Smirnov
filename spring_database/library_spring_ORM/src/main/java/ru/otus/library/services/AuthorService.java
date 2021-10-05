package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.repositories.AuthorRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final FormatterService formatter;

    @Transactional(readOnly = true)
    @ShellMethod(key = "authors", value = "get all authors")
    public void getAll() {
        List<Author> generis = repository.findAll();
        System.out.println(formatter.formatListShortInfo(generis));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "author", value = "get author by id")
    public void getById(long id) {
        Author author = repository.findById(id).get();
        System.out.println(author.getShortInfo());
    }

    @Transactional
    @ShellMethod(key = "delete author", value = "delete author by id")
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @ShellMethod(key = "author update", value = "update author name (name) by id")
    public void update(String name, long id) {
        repository.updateNameById(id, name);
    }

    @Transactional
    @ShellMethod(key = "save author", value = "save author (id, name, surname, middle name)")
    public void save(String name, String surname, String middleName, long id) {
        System.out.println(repository.save(new Author(id, name, surname, middleName)).getFullInfo());
    }
}
