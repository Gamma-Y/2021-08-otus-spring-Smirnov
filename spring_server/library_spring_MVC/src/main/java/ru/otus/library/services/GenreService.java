package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository repository;

    public List<Genre> getAll() {
        List<Genre> generis = repository.findAll();
        return generis;
    }

    public Genre getById(long id) {
        Genre genre = repository.findById(id).get();
        return genre;
    }

    public List<Book> getAllBookByGenreId(long id) {
        Genre genre = repository.findById(id).get();
        return genre.getBooks();
    }

    public void deleteById(long id) {
        Genre genre = repository.findById(id).get();
        repository.delete(genre);
    }

    public Genre update(String title, long id) {
        Genre genre = repository.findById(id).get();
        genre.setTitle(title);
        repository.save(genre);
        return genre;
    }

    public void update(Genre genre) {
        repository.save(genre);
    }

    public void save(String title) {
        repository.save(new Genre(title));
    }


}
