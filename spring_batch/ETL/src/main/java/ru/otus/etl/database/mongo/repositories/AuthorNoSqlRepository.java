package ru.otus.etl.database.mongo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.etl.database.mongo.model.AuthorDocument;

public interface AuthorNoSqlRepository extends MongoRepository<AuthorDocument, ObjectId> {

}
