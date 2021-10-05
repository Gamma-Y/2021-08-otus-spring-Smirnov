package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.database.repositories.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class GenreRepositoryJPA implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public void updateNameById(long id, String title) {

    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from generis g " +
                "where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
