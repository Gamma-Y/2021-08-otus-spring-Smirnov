package ru.otus.etl.database.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.etl.database.relational.entity.GenreEntity;

public interface GenreSqlRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity findByTitle(String title);
}
