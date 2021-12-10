package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.AuthorRepository;
import ru.otus.library.database.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final SequenceService sequenceService;

    @ShellMethod(key = "books", value = "get all books")
    public List<Book> getAll() {
        List<Book> books = repository.findAll();
        return books;
    }

    @ShellMethod(key = "book", value = "get book by id")
    public Book getById(ObjectId id) {
        Book book = repository.findById(id).get();
        return book;
    }

    @ShellMethod(key = "delete book", value = "delete book by id")
    public String deleteById(ObjectId id) {
        Book book = repository.findById(id).get();
        repository.delete(book);
        return book + " deleted";
    }

    @ShellMethod(key = "book update", value = "update book (name) by id")
    public String update(String name, ObjectId id) {
        Book book = repository.findById(id).get();
        book.setName(name);
        repository.save(book);
        return book + " update";
    }

    @ShellMethod(key = "add book", value = "add book(book name, generis, authors Ids)")
    public String save(String name, List<String> generis, ObjectId[] authorsId) {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAllById(List.of(authorsId)).forEach(author -> authors.add(author));
        Book book = new Book(name, generis, authors);
        repository.insert(book);
        return "OK";
    }

    @ShellMethod(key = "book comments", value = "get book comments by book id")
    public List<Comment> getBookCommentsByBookId(ObjectId id) {
        Book book = repository.findById(id).get();
        return book.getComments();
    }

    @ShellMethod(key = "book add comment", value = "add comment to book by book id")
    public String addCommentToBookByBookId(ObjectId id, String commentText) {
        Book book = repository.findById(id).get();
        book.addComment(new Comment(sequenceService.generateSequence(Comment.SEQUENCE_NAME), commentText, System.currentTimeMillis()));
        repository.save(book);
        return "OK";
    }

    @ShellMethod(key = "book delete comment", value = "delete comment to book by book id")
    public String deleteCommentToBookByCommentId(ObjectId id, Long commentId) {
        Book book = repository.findById(id).get();
        book.removeComment(book.getComments().stream().filter(comment -> comment.getId() == commentId).findFirst().get());
        repository.save(book);
        return "OK";
    }

    @ShellMethod(key = "books-genre", value = "delete comment to book by book id")
    public List<Book> getBooksByGenre(String genre) {
        return repository.findByGeneris(genre);
    }
}
