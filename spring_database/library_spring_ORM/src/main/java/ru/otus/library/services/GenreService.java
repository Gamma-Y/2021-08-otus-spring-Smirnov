package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class GenreService {
    private final GenreRepository repository;

    @ShellMethod(key = "generis", value = "get all genres")
    public List<Genre>  getAll() {
        List<Genre> generis = repository.findAll();
        return generis;
    }

    @ShellMethod(key = "genre", value = "get genre by id")
    public void getById(long id) {
        Genre genre = repository.findById(id).get();
        System.out.println(genre);
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "genre book", value = "get all books by genre id")
    public void getAllBookByGenreId(long id) {
        Genre genre = repository.findById(id).get();
        List<Book> books = genre.getBooks();
        System.out.println(genre);
        for(Book b :books){
            System.out.println(b);
        }
    }
    @Transactional
    @ShellMethod(key = "delete genre", value = "delete genre by id")
    public void deleteById(long id) {
        repository.delete(repository.findById(id).get());
    }

    @Transactional
    @ShellMethod(key = "genre update", value = "update genre (title) by id")
    public void update(String title, long id) {
        Genre genre = repository.findById(id).get();
        genre.setTitle(title);
        repository.update(genre);
    }

    @Transactional
    @ShellMethod(key = "save genre", value = "save genre (id, title)")
    public void save(String title) {
        System.out.println(repository.save(new Genre(title)));
    }


}
