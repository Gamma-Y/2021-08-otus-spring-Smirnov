package ru.otus.library.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.library.controllers.dto.AuthorDTO;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.controllers.dto.CommentDTO;
import ru.otus.library.controllers.dto.GenreDTO;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookRepository repository;

    @GetMapping("/delete/book/{id}")
    public Mono<Boolean> deleteBookById(@PathVariable("id") String id) {
        repository.deleteById(id);
        return Mono.just(true);
    }


    @PostMapping("/book/add/genre")
    public Mono<Book> addGenreToBook(@RequestBody GenreDTO newGenre) {
        Mono<Book> book = repository.findById(newGenre.getBookId());
        book.map(b -> b.getGeneris().add(newGenre.getTitle()));
        return repository.save(book.block());

    }

    @PostMapping(value = "/book/add/author")
    public Mono<Book> addAuthorToBook(@RequestBody AuthorDTO newAuthor) {
        Mono<Book> book = repository.findById(newAuthor.getBookId());
        book.map(b -> b.getAuthors().add(new Author(newAuthor.getAuthor())));
        return repository.save(book.block());

    }

    @PostMapping(value = "/book/add/comment")
    public Mono<Book> addCommentToBook(@RequestBody CommentDTO newComment) {
        Mono<Book> book = repository.findById(newComment.getBookId());
        book.map(b -> b.getComments().add(new Comment(newComment.getTimestamp(), newComment.getText())));
        return repository.save(book.block());

    }

    @PostMapping(value = "/update/book")
    public Mono<Book> updateBookName(@RequestBody BookDTO updateBook) {
        Mono<Book> book = repository.findById(updateBook.getBookId());
        book.map(b -> {
            b.setName(updateBook.getName());
            return b;
        });
        return repository.save(book.block());

    }

    @PostMapping(value = "/author/delete")
    public Mono<Book> deleteAuthorFromBook(@RequestBody AuthorDTO deleteAuthor) {
        Mono<Book> book = repository.findById(deleteAuthor.getBookId());
        book.map(b -> {
            b.getAuthors().remove(new Author(deleteAuthor.getAuthor()));
            return b;
        });
        return repository.save(book.block());

    }

    @PostMapping(value = "/genre/delete")
    public Mono<Book> deleteGenreFromBook(@RequestBody GenreDTO deleteGenre) {
        Mono<Book> book = repository.findById(deleteGenre.getBookId());
        book.map(b -> {
            b.getGeneris().remove(deleteGenre.getTitle());
            return b;
        });
        return repository.save(book.block());

    }

    @PostMapping(value = "/comment/delete")
    public Mono<Book> deleteCommentFromBook(@RequestBody CommentDTO deleteComment) {
        Mono<Book> book = repository.findById(deleteComment.getBookId());
        book.map(b -> {
            b.getComments().remove(new Comment(deleteComment.getTimestamp(), deleteComment.getText()));
            return b;
        });
        return repository.save(book.block());

    }
}
