package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
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
    public Genre getById(long id) {
        Genre genre = repository.findById(id).get();
        return genre;
    }

    @ShellMethod(key = "genre book", value = "get all books by genre id")
    public void getAllBookByGenreId(long id) {
        Genre genre = repository.findById(id).get();
        List<Book> books = genre.getBooks();
        System.out.println(genre);
        for(Book b :books){
            System.out.println(b);
        }
    }

    @ShellMethod(key = "delete genre", value = "delete genre by id")
    public String deleteById(long id) {
        Genre genre = repository.findById(id).get();
        repository.delete(genre);
        return genre + " deleted";
    }

    @ShellMethod(key = "genre update", value = "update genre (title) by id")
    public String update(String title, long id) {
        Genre genre = repository.findById(id).get();
        genre.setTitle(title);
        repository.save(genre);
        return genre + " update";
    }

    @ShellMethod(key = "save genre", value = "save genre (id, title)")
    public String save(String title) {
        repository.save(new Genre(title));
        return "OK";
    }


}
