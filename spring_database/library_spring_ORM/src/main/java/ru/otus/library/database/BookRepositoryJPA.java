package ru.otus.library.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.repositories.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJPA implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() >= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public void updateNameById(long id, String name) {
        Query query = em.createQuery("update Book b " +
                "set b.name=:name " +
                "where b.id=:id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
