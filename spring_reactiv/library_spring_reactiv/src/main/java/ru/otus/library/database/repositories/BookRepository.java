package ru.otus.library.database.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.database.entities.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
