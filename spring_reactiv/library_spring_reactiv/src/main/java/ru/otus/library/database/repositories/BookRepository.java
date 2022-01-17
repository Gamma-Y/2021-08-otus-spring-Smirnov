package ru.otus.library.database.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.library.database.entities.Book;

import java.util.List;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByGeneris(String generis);

//    @Query("{'authors.$id':ObjectId('?0')}")
//    List<Book> findByAuthors(ObjectId id);
}
