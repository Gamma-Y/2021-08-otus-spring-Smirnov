package ru.otus.library.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final BookRepository bookRepository;

    public List<Comment> getAll() {
        List<Comment> comments = repository.findAll();
        return comments;
    }

    public Comment getById(long id) {
        Comment comment = repository.findById(id).get();
        return comment;
    }

    public void deleteById(long id) {
        Comment comment = repository.findById(id).get();
        repository.delete(comment);
    }

    public Comment update(String text, long id) {
        Comment comment = repository.findById(id).get();
        comment.setText(text);
        repository.save(comment);
        return comment;
    }

    public void save(String text, long bookId) {
        Book book = bookRepository.findById(bookId).get();
        repository.save(new Comment(text, System.currentTimeMillis()));
    }
}
