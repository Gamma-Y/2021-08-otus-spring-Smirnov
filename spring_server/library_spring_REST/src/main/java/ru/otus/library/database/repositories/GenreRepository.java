package ru.otus.library.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.database.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
