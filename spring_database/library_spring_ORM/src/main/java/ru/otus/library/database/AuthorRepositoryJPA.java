package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.repositories.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorRepositoryJPA implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author save(Author author) {
        if (author.getId() >= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findByIds(List<Long> id) {
        Query query = em.createQuery("select a from Author a where a.id in (:ids)");
        query.setParameter("ids", id);
        return query.getResultList();
    }


    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public void update(Author updatedAuthor) {
        this.save(updatedAuthor);
    }


    @Override
    public void delete(Author author) {
        em.remove(author);
    }
}
