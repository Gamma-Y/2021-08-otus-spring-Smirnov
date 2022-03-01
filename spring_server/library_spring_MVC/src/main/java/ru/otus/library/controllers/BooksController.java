package ru.otus.library.controllers;

import lombok.AllArgsConstructor;
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
    private final BookService service;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping({"/list/book", "/"})
    public String getAllBooks(ModelMap model) {
        model.addAttribute("books", service.getAll());
        return "book/list";
    }

    @GetMapping("/edit/book")
    public String getBookInfoById(@RequestParam long id, ModelMap model) {
        model.addAttribute("book", service.getById(id));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("generis", genreService.getAll());
        return "book/edit";
    }

    @PostMapping("/edit/book")
    public String saveBookInfo(@RequestParam Map<String, String> allParams) {
        String id = allParams.get("id");
        service.updateByAttributeMap(allParams);
        return "redirect:/edit/book?id=" + id;
    }

    @GetMapping("/delete/book")
    public String deleteBookById(@RequestParam long id) {
        service.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/delete/book/comment")
    public String deleteCommentFromBookById(@RequestParam long bookId, @RequestParam long commentId) {
        commentService.deleteById(commentId);
        return "redirect:/edit/book?id=" + bookId;
    }

    @GetMapping("/delete/book/genre")
    public String deleteGenreFromBookById(@RequestParam long bookId, @RequestParam long genreId) {
        service.deleteGenreFromBook(bookId, genreId);
        return "redirect:/edit/book?id=" + bookId;
    }

    @GetMapping("/delete/book/author")
    public String deleteAuthorFromBookById(@RequestParam long bookId, @RequestParam long authorId) {
        service.deleteAuthorFromBook(bookId, authorId);
        return "redirect:/edit/book?id=" + bookId;
    }

}
