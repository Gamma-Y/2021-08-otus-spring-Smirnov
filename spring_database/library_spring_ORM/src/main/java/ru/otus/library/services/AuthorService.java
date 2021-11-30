package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.AuthorRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    @ShellMethod(key = "authors", value = "get all authors")
    public List<Author>  getAll() {
        List<Author> authors = repository.findAll();
        return authors ;
    }


    @ShellMethod(key = "author", value = "get author by id")
    public Author getById(long id) {
        Author author = repository.findById(id).get();
        return  author;
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "author book", value = "get all books by author id")
    public  void getAuthorBooksById(long id) {
        Author author = repository.findById(id).get();
        List<Book> books = author.getBooks();
        System.out.println(author);
        for(Book b :books){
            System.out.println(b);
        }
    }

    @Transactional
    @ShellMethod(key = "delete author", value = "delete author by id")
    public String deleteById(long id) {
        Author author = repository.findById(id).get();
        repository.delete(author);
        return author + " deleted";
    }

    @Transactional
    @ShellMethod(key = "author update", value = "update author name (name) by id")
    public String update(String name, long id) {
        Author author = repository.findById(id).get();
        author.setFullName(name);
        repository.update(author);
        return author + " update";
    }

    @Transactional
    @ShellMethod(key = "add author", value = "save author (id, name, surname, middle name)")
    public String save(String name) {
        repository.save(new Author(name));
        return "OK";
    }



}
