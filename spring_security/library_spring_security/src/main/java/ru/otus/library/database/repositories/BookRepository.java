package ru.otus.library.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.database.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
