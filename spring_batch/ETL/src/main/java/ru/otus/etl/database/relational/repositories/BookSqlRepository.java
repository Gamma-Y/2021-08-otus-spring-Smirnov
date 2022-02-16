package ru.otus.etl.database.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.etl.database.relational.entity.BookEntity;

public interface BookSqlRepository extends JpaRepository<BookEntity, Long> {
}
