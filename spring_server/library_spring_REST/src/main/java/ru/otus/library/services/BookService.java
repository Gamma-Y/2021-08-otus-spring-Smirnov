package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.AuthorRepository;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    public List<Book> getAll() {
        List<Book> books = repository.findAll();
        return books;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackBooksDTO", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public List<BookDTO> getAllHowDTO() {
        List<Book> books = this.getAll();
        List<BookDTO> booksDTO = new ArrayList<>();
        for (Book book : books) {
            booksDTO.add(convertBookToDTO(book));
        }
        return booksDTO;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackBook", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public Book getById(long id) {
        Book book = repository.findById(id).get();
        return book;
    }

    @HystrixCommand(fallbackMethod = "returnStatus", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public boolean deleteById(long id) {
        Book book = repository.findById(id).get();
        repository.delete(book);
        return true;
    }

    public Book update(String name, long id) {
        Book book = repository.findById(id).get();
        book.setName(name);
        repository.save(book);
        return book;
    }

    public void save(String name, Long[] generisId, Long[] authorsId) {
        List<Genre> generis = genreRepository.findAllById(List.of(generisId));
        List<Author> authors = authorRepository.findAllById(List.of(authorsId));
        Book book = new Book(name, authors, generis);
        repository.save(book);
    }

    @HystrixCommand(fallbackMethod = "returnStatus", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public boolean deleteGenreFromBook(long bookId, long genreId) {
        Book book = repository.getOne(bookId);
        List<Genre> genres = book.getGeneris();
        for (Genre genre : genres) {
            if (genre.getId() == genreId) {
                genres.remove(genre);
                break;
            }
        }
        repository.save(book);
        return true;
    }

    @HystrixCommand(fallbackMethod = "returnStatus", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public boolean deleteAuthorFromBook(long bookId, long authorId) {
        Book book = repository.getOne(bookId);
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            if (author.getId() == authorId) {
                authors.remove(author);
                break;
            }
        }
        repository.save(book);
        return true;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackBookDTO", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public BookDTO updateBook(BookDTO bookDTO) {
        Book book = repository.getOne(bookDTO.getId());
        book.setName(bookDTO.getName());
        return convertBookToDTO(repository.save(book));
    }


    private BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        List<String> authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            authors.add(author.getFullName());
        }
        bookDTO.setAuthors(authors);
        List<String> genres = new ArrayList<>();
        for (Genre genre : book.getGeneris()) {
            genres.add(genre.getTitle());
        }
        bookDTO.setGenres(genres);
        return bookDTO;
    }

    public List<BookDTO> buildFallbackBooksDTO() {
        System.out.println("test");
        return List.of(buildFallbackBookDTO());
    }

    public BookDTO buildFallbackBookDTO() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("N/A");
        bookDTO.setId(-1);
        bookDTO.setAuthors(List.of("N/A"));
        bookDTO.setGenres(List.of("N/A"));

        return bookDTO;
    }

    public BookDTO buildFallbackBookDTO(BookDTO oldBookDTO) {
        BookDTO bookDTO = buildFallbackBookDTO();
        return bookDTO;
    }

    public Book buildFallbackBook(BookDTO oldBookDTO) {
        Book book = new Book();
        book.setName("N/A");
        book.setId(-1);
        book.setAuthors(List.of(new Author("N/A")));
        book.setComments(List.of(new Comment("N/A", -1)));
        book.setGeneris(List.of(new Genre("N/A")));
        return book;
    }

    public boolean returnStatus(long... id) {
        return false;
    }
}
