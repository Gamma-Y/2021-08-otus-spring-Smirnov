package ru.otus.library.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.database.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
