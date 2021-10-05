package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.repositories.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorRepositoryJPA implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author save(Author author) {
        return null;
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public void updateId(Author author) {

    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from authors a " +
                "where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
