package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
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

    public List<BookDTO> getAllHowDTO() {
        List<Book> books = this.getAll();
        List<BookDTO> booksDTO = new ArrayList<>();
        for (Book book : books) {
            booksDTO.add(convertBookToDTO(book));
        }
        return booksDTO;
    }

    public Book getById(long id) {
        Book book = repository.findById(id).get();
        return book;
    }

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

    public boolean deleteGenreFromBook(long bookId, long genreId) {
        Book book = repository.getById(bookId);
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

    public boolean deleteAuthorFromBook(long bookId, long authorId) {
        Book book = repository.getById(bookId);
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

    public BookDTO updateBook(BookDTO bookDTO) {
        Book book = repository.getById(bookDTO.getId());
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
}
