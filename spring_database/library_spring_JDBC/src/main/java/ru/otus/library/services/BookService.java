package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.dao.BookDao;
import ru.otus.library.database.entities.Book;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final FormatterService formatter;

    @ShellMethod(key = "books", value = "get all books")
    public String getAll() {
        List<Book> books = bookDao.getAll();
        return formatter.formatList(books);
    }
}
