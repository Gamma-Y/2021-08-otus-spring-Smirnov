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
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    @ShellMethod(key = "books", value = "get all books")
    public List<Book>  getAll() {
        List<Book> books = repository.findAll();
        return books;
    }

    @ShellMethod(key = "book", value = "get book by id")
    public Book getById(long id) {
        Book book = repository.findById(id).get();
        return book;
    }

    @Transactional
    @ShellMethod(key = "delete book", value = "delete book by id")
    public String deleteById(long id) {
        Book book = repository.findById(id).get();
        repository.delete(book);
        return book + " deleted";
    }

    @Transactional
    @ShellMethod(key = "book update", value = "update book (name) by id")
    public String update(String name, long id) {
        Book book = repository.findById(id).get();
        book.setName(name);
        repository.update(book);
        return book + " update";
    }

    @Transactional
    @ShellMethod(key = "add book", value = "add book(book name, generis Ids, authors Ids)")
    public String save(String name, Long[] generisId, Long[] authorsId) {
        List<Genre> generis = genreRepository.findByIds(List.of(generisId));
        List<Author> authors = authorRepository.findByIds(List.of(authorsId));
        Book book = new Book(name,authors,generis);
        repository.save(book);
        return "OK";
    }
}
