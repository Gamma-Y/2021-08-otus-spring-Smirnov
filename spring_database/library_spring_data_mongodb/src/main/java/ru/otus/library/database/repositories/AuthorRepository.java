package ru.otus.library.database.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.database.entities.Author;

public interface AuthorRepository extends MongoRepository<Author, ObjectId> {

}
