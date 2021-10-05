package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepositoryJPA implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() >= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public void updateTextById(long id, String text, long dateTime) {
        Query query = em.createQuery("update Comment c " +
                "set c.text=:text, c.dateTime=:dateTime " +
                "where c.id=:id");
        query.setParameter("text", text);
        query.setParameter("dataTime", dateTime);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
