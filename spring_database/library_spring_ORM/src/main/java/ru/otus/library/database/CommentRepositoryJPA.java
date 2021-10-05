package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepositoryJPA implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public void updateId(Comment comment) {

    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from comments c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
