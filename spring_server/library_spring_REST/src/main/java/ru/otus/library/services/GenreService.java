package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    @HystrixCommand(fallbackMethod = "buildFallbackGenres", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
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

    @HystrixCommand(fallbackMethod = "buildFallbackGenre", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public GenreDTO addGenreToBook(GenreDTO genreDTO) {
        Book book = bookService.getById(genreDTO.getBookId());
        Genre genre = repository.getOne(genreDTO.getId());
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

    public GenreDTO buildFallbackGenre() {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setBookId(-1);
        genreDTO.setId(-1);
        genreDTO.setTitle("N/A");

        return genreDTO;
    }

    public GenreDTO buildFallbackAuthor(GenreDTO oldGenreDTO) {
        GenreDTO genreDTO = buildFallbackGenre();
        return genreDTO;
    }

    public List<Genre> buildFallbackGenres() {
        Genre genre = new Genre("N/A");
        return List.of(genre);

    }

}
