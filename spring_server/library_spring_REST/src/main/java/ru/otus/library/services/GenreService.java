package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.controllers.dto.GenreDTO;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository repository;
    private final BookService bookService;

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

    public GenreDTO addGenreToBook(GenreDTO genreDTO) {
        Book book = bookService.getById(genreDTO.getBookId());
        Genre genre = repository.getById(genreDTO.getId());
        genre.addBook(book);
        repository.save(genre);
        genreDTO.setTitle(genre.getTitle());
        return genreDTO;
    }

    public void update(Genre genre) {
        repository.save(genre);
    }

    public void save(String title) {
        repository.save(new Genre(title));
    }


}
