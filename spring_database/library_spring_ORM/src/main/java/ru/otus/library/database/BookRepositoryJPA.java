package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJPA implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public void updateId(Book book) {

    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from books b " +
                "where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
