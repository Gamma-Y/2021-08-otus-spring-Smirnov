package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.AuthorRepository;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;
import ru.otus.library.database.repositories.GenreRepository;

import java.util.List;
import java.util.Map;

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

    public Book getById(long id) {
        Book book = repository.findById(id).get();
        return book;
    }

    public String deleteById(long id) {
        Book book = repository.findById(id).get();
        repository.delete(book);
        return book + " deleted";
    }

    public Book update(String name, long id) {
        Book book = repository.findById(id).get();
        book.setName(name);
        repository.save(book);
        return book;
    }

    public String save(String name, Long[] generisId, Long[] authorsId) {
        List<Genre> generis = genreRepository.findAllById(List.of(generisId));
        List<Author> authors = authorRepository.findAllById(List.of(authorsId));
        Book book = new Book(name, authors, generis);
        repository.save(book);
        return "OK";
    }

    public void deleteGenreFromBook(long bookId, long genreId) {
        Book book = repository.getById(bookId);
        List<Genre> genres = book.getGeneris();
        for (Genre genre : genres) {
            if (genre.getId() == genreId) {
                genres.remove(genre);
                break;
            }
        }
        repository.save(book);
    }

    public void deleteAuthorFromBook(long bookId, long authorId) {
        Book book = repository.getById(bookId);
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            if (author.getId() == authorId) {
                authors.remove(author);
                break;
            }
        }
        repository.save(book);
    }

    public void updateByAttributeMap(Map<String, String> attribute){
        Book book = repository.getById(Long.parseLong(attribute.get("id")));
        book.setName(attribute.get("name"));
        if(!attribute.get("newAuthor").isBlank()){
            Author author = authorRepository.getById(Long.parseLong(attribute.get("newAuthor")));
            if(!book.getAuthors().contains(author)) book.getAuthors().add(author);
        }
        if(!attribute.get("newGenre").isBlank()){
            Genre genre = genreRepository.getById(Long.parseLong(attribute.get("newGenre")));
            if(!book.getGeneris().contains(genre)) book.getGeneris().add(genre);
        }
        if(!attribute.get("comment").isBlank()){
            book.addComment(new Comment(attribute.get("comment"),System.currentTimeMillis(), book));
        }

        repository.save(book);
    }
}
