package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.controllers.dto.AuthorDTO;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.AuthorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final BookService bookService;

    @HystrixCommand(fallbackMethod = "buildFallbackAuthors", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public List<Author> getAll() {
        List<Author> authors = repository.findAll();
        return authors;
    }

    public Author getById(long id) {
        Author author = repository.findById(id).get();
        return author;
    }

    public List<Book> getAuthorBooksById(long id) {
        Author author = repository.findById(id).get();
        return author.getBooks();
    }

    public void deleteById(long id) {
        Author author = repository.findById(id).get();
        repository.delete(author);
    }

    public Author update(String name, long id) {
        Author author = repository.findById(id).get();
        author.setFullName(name);
        repository.save(author);
        return author;
    }

    public Author update(Author author) {
        repository.save(author);
        return author;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackAuthor", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public AuthorDTO addBookToAuthor(AuthorDTO authorDTO) {
        Book book = bookService.getById(authorDTO.getBookId());
        Author author = repository.getOne(authorDTO.getId());
        author.addBook(book);
        repository.save(author);
        authorDTO.setFullName(author.getFullName());
        return authorDTO;
    }


    public void save(String name) {
        repository.save(new Author(name));
    }

    public AuthorDTO buildFallbackAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setBookId(-1);
        authorDTO.setId(-1);
        authorDTO.setFullName("N/A");

        return authorDTO;
    }

    public AuthorDTO buildFallbackAuthor(AuthorDTO oldAuthorDTO) {
        AuthorDTO authorDTO = buildFallbackAuthor();
        return authorDTO;
    }

    public List<Author> buildFallbackAuthors() {
        Author author = new Author("N/A");
        return List.of(author);

    }

}
