package ru.otus.library.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.database.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
