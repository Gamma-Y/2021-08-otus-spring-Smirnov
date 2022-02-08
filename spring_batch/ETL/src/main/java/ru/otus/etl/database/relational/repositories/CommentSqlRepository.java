package ru.otus.etl.database.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.etl.database.relational.entity.CommentEntity;

public interface CommentSqlRepository extends JpaRepository<CommentEntity,Long> {
}
