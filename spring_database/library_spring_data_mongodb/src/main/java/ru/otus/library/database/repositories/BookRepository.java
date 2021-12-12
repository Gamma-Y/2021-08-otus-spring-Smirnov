package ru.otus.library.database.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.library.database.entities.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, ObjectId> {
    List<Book> findByGeneris(String generis);

    @Query("{'authors.$id':ObjectId('?0')}")
    List<Book> findByAuthors(ObjectId id);
}
