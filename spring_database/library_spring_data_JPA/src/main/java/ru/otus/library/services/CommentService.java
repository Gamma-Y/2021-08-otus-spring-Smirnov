package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final BookRepository bookRepository;

    @ShellMethod(key = "comments", value = "get all comments")
    public List<Comment> getAll() {
        List<Comment> comments = repository.findAll();
        return comments;
    }

    @ShellMethod(key = "comment", value = "get comment by id")
    public Comment getById(long id) {
        Comment comment = repository.findById(id).get();
        return comment;
    }

    @ShellMethod(key = "delete comment", value = "delete comment by id")
    public String deleteById(long id) {
        Comment comment = repository.findById(id).get();
        repository.delete(comment);
        return comment + " deleted";
    }

    @ShellMethod(key = "comment update", value = "update comment (text, dateTime) by id")
    public String update(String text, long id) {
        Comment comment = repository.findById(id).get();
        comment.setText(text);
        repository.save(comment);
        return comment + " update";
    }

    @ShellMethod(key = "save comment", value = "save comment (text, bookId)")
    public String save(String text, long bookId) {
        Book book = bookRepository.findById(bookId).get();
        repository.save(new Comment(text, System.currentTimeMillis(), book));
        return "OK";
    }
}
