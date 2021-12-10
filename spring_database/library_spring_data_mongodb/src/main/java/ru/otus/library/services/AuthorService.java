package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.AuthorRepository;
import ru.otus.library.database.repositories.BookRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @ShellMethod(key = "authors", value = "get all authors")
    public List<Author>  getAll() {
        List<Author> authors = repository.findAll();
        return authors ;
    }


    @ShellMethod(key = "author", value = "get author by id")
    public Author getById(ObjectId id) {
        Author author = repository.findById(id).get();
        return  author;
    }

    @ShellMethod(key = "author book", value = "get all books by author id")
    public  List<Book> getAuthorBooksById(ObjectId id) {
        return bookRepository.findByAuthors(id);
    }

    @ShellMethod(key = "delete author", value = "delete author by id")
    public String deleteById(ObjectId id) {
        Author author = repository.findById(id).get();
        repository.delete(author);
        return author + " deleted";
    }

    @ShellMethod(key = "author update", value = "update author name (name) by id")
    public String update(String name, ObjectId id) {
        Author author = repository.findById(id).get();
        author.setFullName(name);
        repository.save(author);
        return author + " update";
    }

    @ShellMethod(key = "add author", value = "save author (id, full name)")
    public String save(String name) {
        repository.insert(new Author(name));
        return "OK";
    }



}
