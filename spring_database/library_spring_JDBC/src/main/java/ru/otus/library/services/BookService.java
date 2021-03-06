package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.dao.BookDao;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Genre;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final FormatterService formatter;

    @ShellMethod(key = "books", value = "get all books")
    public String getAll() {
        List<Book> books = bookDao.getAll();
        return formatter.formatListShortInfo(books);
    }

    @ShellMethod(key = "book", value = "get book by id")
    public String getById(long id) {
        return bookDao.getById(id).toString();
    }

    @ShellMethod(key = "new-book", value = "insert new book (id, name, authorId, genreId)")
    public void insert(long id, String name, long genreId, long authorId) {
        Book book = new Book(id, name, new Author(authorId, null, null, null), new Genre(genreId, null));
        bookDao.insert(book);
    }

    @ShellMethod(key = "delete-book", value = "delete book by id")
    public void delete(long id) {
        bookDao.deleteById(id);
    }


}
