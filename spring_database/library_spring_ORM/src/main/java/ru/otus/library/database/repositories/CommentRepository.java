package ru.otus.library.database.repositories;

import ru.otus.library.database.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    void updateId(Comment comment);

    void deleteById(long id);
}
