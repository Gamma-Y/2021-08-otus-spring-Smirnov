package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.AuthorRepository;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final FormatterService formatter;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @ShellMethod(key = "books", value = "get all books")
    public void getAll() {
        List<Book> generis = repository.findAll();
        System.out.println(formatter.formatListShortInfo(generis));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "book", value = "get book by id")
    public void getById(long id) {
        Book book = repository.findById(id).get();
        System.out.println(book.getFullInfo());
    }

    @Transactional
    @ShellMethod(key = "delete book", value = "delete book by id")
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @ShellMethod(key = "book update", value = "update book (name) by id")
    public void update(String name, long id) {
        repository.updateNameById(id, name);
    }

    @Transactional
    @ShellMethod(key = "add book", value = "add book(book name, generis Ids, authors Ids)")
    public void save(String name, Long[] generisId, Long[] authorsId) {
        List<Genre> generis = genreRepository.findById(List.of(generisId));
        List<Author> authors = authorRepository.findById(List.of(authorsId));
        Book book = new Book(name,authors,generis);
        repository.save(book);
    }
}
