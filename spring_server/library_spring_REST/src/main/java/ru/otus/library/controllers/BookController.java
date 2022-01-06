package ru.otus.library.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.controllers.dto.AuthorDTO;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.controllers.dto.CommentDTO;
import ru.otus.library.controllers.dto.GenreDTO;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookService service;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping("/books")
    public List<BookDTO> getAllBooks() {
        return service.getAllHowDTO();
    }

    @PostMapping("/delete/book")
    public boolean deleteBookById(@RequestParam long id) {
        return service.deleteById(id);
    }

    @PostMapping("/delete/book/author")
    public boolean deleteAuthorFromBookById(@RequestParam long bookId, @RequestParam long authorId) {
        return service.deleteAuthorFromBook(bookId, authorId);
    }

    @PostMapping("/delete/book/genre")
    public boolean deleteGenreFromBookById(@RequestParam long bookId, @RequestParam long genreId) {
        return service.deleteGenreFromBook(bookId, genreId);
    }

    @PostMapping("/delete/book/comment")
    public boolean deleteCommentFromBookById(@RequestParam long commentId) {
        return commentService.deleteById(commentId);
    }

    @PostMapping("/add/book/comment")
    public CommentDTO addCommentToBookById(@ModelAttribute CommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    @PostMapping("/update/book")
    public BookDTO updateBook(@ModelAttribute BookDTO bookDTO) {
        return service.updateBook(bookDTO);
    }

    @PostMapping("/add/book/author")
    public AuthorDTO addAuthorToBook(@ModelAttribute AuthorDTO authorDTO) {
        return authorService.addBookToAuthor(authorDTO);
    }

    @PostMapping("/add/book/genre")
    public GenreDTO addGenreToBook(@ModelAttribute GenreDTO genreDTO) {
        return genreService.addGenreToBook(genreDTO);
    }


}
