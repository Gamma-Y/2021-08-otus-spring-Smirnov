package ru.otus.library.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

import java.util.Map;

@Controller
@AllArgsConstructor
public class BooksController {
    private final Logger logger = LoggerFactory.getLogger(BooksController.class);
    private final MeterRegistry registry;
    private final BookService service;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping({"/list/book", "/"})
    @Timed(value = "time.book.all")
    public String getAllBooks(ModelMap model) {
        registry.counter("request.book.all").increment();
        logger.info("request all book");
        model.addAttribute("books", service.getAll());
        return "book/list";
    }

    @GetMapping("/edit/book")
    @Timed(value = "time.book.info")
    public String getBookInfoById(@RequestParam long id, ModelMap model) {
        registry.counter("request.book.info").increment();
        logger.info("request book info: " + id);
        model.addAttribute("book", service.getById(id));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("generis", genreService.getAll());
        return "book/edit";
    }

    @PostMapping("/edit/book")
    @Timed(value = "time.book.save")
    public String saveBookInfo(@RequestParam Map<String, String> allParams) {
        String id = allParams.get("id");
        logger.info("request to save book info: " + id);
        service.updateByAttributeMap(allParams);
        registry.counter("request.book.save").increment();
        return "redirect:/edit/book?id=" + id;
    }

    @GetMapping("/delete/book")
    @Timed(value = "time.book.delete")
    public String deleteBookById(@RequestParam long id) {
        logger.info("request to delete book: " + id);
        registry.counter("request.book.delete").increment();
        service.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/delete/book/comment")
    @Timed(value = "time.book.delete.comment")
    public String deleteCommentFromBookById(@RequestParam long bookId, @RequestParam long commentId) {
        logger.info("request to delete comment: " + commentId + " to book: " + bookId);
        commentService.deleteById(commentId);
        registry.counter("request.book.delete.comment").increment();
        return "redirect:/edit/book?id=" + bookId;
    }

    @GetMapping("/delete/book/genre")
    @Timed(value = "time.book.delete.genre")
    public String deleteGenreFromBookById(@RequestParam long bookId, @RequestParam long genreId) {
        logger.info("request to delete genre: " + genreId + " to book: " + bookId);
        service.deleteGenreFromBook(bookId, genreId);
        registry.counter("request.book.delete.genre").increment();
        return "redirect:/edit/book?id=" + bookId;
    }

    @GetMapping("/delete/book/author")
    @Timed(value = "time.book.delete.author")
    public String deleteAuthorFromBookById(@RequestParam long bookId, @RequestParam long authorId) {
        logger.info("request to delete author: " + authorId + " to book: " + bookId);
        service.deleteAuthorFromBook(bookId, authorId);
        registry.counter("request.book.delete.author").increment();
        return "redirect:/edit/book?id=" + bookId;
    }
}
