package ru.otus.library.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

@Controller
@AllArgsConstructor
public class PageController {
    private final BookService service;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping("/list/book")
    public String getAllBooks() {
        return "book/list";
    }

    @GetMapping("/edit/book")
    public String getBookInfoById(@RequestParam long id, ModelMap model) {
        model.addAttribute("book", service.getById(id));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("generis", genreService.getAll());
        return "book/edit";
    }
}
