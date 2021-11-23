package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.CommentRepository;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final FormatterService formatter;

    @Transactional(readOnly = true)
    @ShellMethod(key = "comments", value = "get all comments")
    public void getAll() {
        List<Comment> generis = repository.findAll();
        System.out.println(formatter.formatListShortInfo(generis));
    }

    @Transactional(readOnly = true)
    @ShellMethod(key = "comment", value = "get comment by id")
    public void getById(long id) {
        Comment comment = repository.findById(id).get();
        System.out.println(comment.getShortInfo());
    }

    @Transactional
    @ShellMethod(key = "delete comment", value = "delete comment by id")
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @ShellMethod(key = "comment update", value = "update comment (text, dateTime) by id")
    public void update(String text, long id) {
        repository.updateTextById(id, text, System.currentTimeMillis());
    }

    @Transactional
    @ShellMethod(key = "save comment", value = "save comment (text, dateTime)")
    public void save(String text, long dateTime) {
        System.out.println(repository.save(new Comment(text, dateTime)).getFullInfo());
    }
}
