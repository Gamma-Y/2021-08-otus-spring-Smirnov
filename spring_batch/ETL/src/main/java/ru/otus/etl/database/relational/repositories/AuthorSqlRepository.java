package ru.otus.etl.database.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.etl.database.relational.entity.AuthorEntity;

public interface AuthorSqlRepository extends JpaRepository<AuthorEntity, Long> {
    AuthorEntity findByFullName(String fullName);
}
