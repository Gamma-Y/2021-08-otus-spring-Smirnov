package ru.otus.library.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.database.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
