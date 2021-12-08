package ru.otus.library.database.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.database.entities.Book;

public interface BookRepository extends MongoRepository<Book, ObjectId> {

}
