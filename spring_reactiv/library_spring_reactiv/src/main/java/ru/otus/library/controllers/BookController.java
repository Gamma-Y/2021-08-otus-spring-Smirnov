package ru.otus.library.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.endpoints.DTO.AuthorDTO;
import ru.otus.library.endpoints.DTO.BookDTO;
import ru.otus.library.endpoints.DTO.CommentDTO;
import ru.otus.library.endpoints.DTO.GenreDTO;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookRepository repository;

    @GetMapping("/delete/book/{id}")
    public Mono<Boolean> deleteBookById(@PathVariable("id") String id) {
        repository.deleteById(id);
        return Mono.just(true);
    }

    @PostMapping(value = "/book/add/genre")
    public Mono<Book> addGenreToBook(@ModelAttribute Mono<GenreDTO> newGenre) {
        return repository.findById(newGenre.map(GenreDTO::getBookId))
                .log()
                .doOnNext(book -> book.getGeneris().add(newGenre.block().getTitle()))
                .log()
                .flatMap(repository::save);

    }

    @PostMapping(value = "/book/add/author")
    public Mono<Book> addAuthorToBook(@ModelAttribute Mono<AuthorDTO> newAuthor) {
        return repository.findById(newAuthor.map(AuthorDTO::getBookId))
                .log()
                .doOnNext(book -> book.getAuthors().add(new Author(newAuthor.block().getAuthor())))
                .log()
                .flatMap(repository::save);

    }

    @PostMapping(value = "/book/add/comment")
    public Mono<Book> addCommentToBook(@ModelAttribute Mono<CommentDTO> newComment) {
        CommentDTO commentDTO = newComment.block();
        return repository.findById(newComment.map(CommentDTO::getBookId))
                .log()
                .doOnNext(book -> book.getComments().add(new Comment(commentDTO.getTimestamp(), commentDTO.getText())))
                .log()
                .flatMap(repository::save);

    }

    @PostMapping(value = "/update/book")
    public Mono<Book> updateBookName(@ModelAttribute Mono<BookDTO> updateBook) {
        return repository.findById(updateBook.map(BookDTO::getBookId))
                .log()
                .doOnNext(book -> book.setName(updateBook.map(BookDTO::getName).block()))
                .log()
                .flatMap(repository::save);

    }

    @PostMapping(value = "/author/delete")
    public Mono<Book> deleteAuthorFromBook(@ModelAttribute Mono<AuthorDTO> deleteAuthor) {
        return repository.findById(deleteAuthor.map(AuthorDTO::getBookId))
                .log()
                .doOnNext(book -> book.getAuthors().remove(new Author(deleteAuthor.block().getAuthor())))
                .log()
                .flatMap(repository::save);

    }

    @PostMapping(value = "/genre/delete")
    public Mono<Book> deleteGenreFromBook(@ModelAttribute Mono<GenreDTO> deleteGenre) {
        return repository.findById(deleteGenre.map(GenreDTO::getBookId))
                .log()
                .doOnNext(book -> book.getGeneris().remove(deleteGenre.block().getTitle()))
                .log()
                .flatMap(repository::save);

    }

    @PostMapping(value = "/comment/delete")
    public Mono<Book> deleteCommentFromBook(@ModelAttribute Mono<CommentDTO> deleteComment) {
        CommentDTO commentDTO = deleteComment.block();
        return repository.findById(commentDTO.getBookId())
                .log()
                .doOnNext(book -> book.getComments().remove(new Comment(commentDTO.getTimestamp(), commentDTO.getText())))
                .log()
                .flatMap(repository::save);

    }
}
