package ru.otus.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.library.controllers.dto.BookDTO;

import java.util.List;

@Controller
public class PageController {
    @GetMapping("/list/book")
    public String getAllBooks() {
        return "book/list";
    }
}
