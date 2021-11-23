package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.AuthorRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    @ShellMethod(key = "author book", value = "get all books by author id")
    public void getAuthorBookById(long id) {
        Author authors = repository.findById(id).get();
        List<Book> books = authors.getBooks();
        for(Book b :books){
            System.out.println(b.getShortInfo());
        }
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
    @ShellMethod(key = "add author", value = "save author (id, name, surname, middle name)")
    public void save(String name) {
        System.out.println(repository.save(new Author(name)).getFullInfo());
    }



}
