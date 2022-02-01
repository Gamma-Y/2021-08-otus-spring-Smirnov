package ru.otus.etl.database.mongo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.etl.database.mongo.model.Author;

public interface AuthorRepository extends MongoRepository<Author, ObjectId> {

}
