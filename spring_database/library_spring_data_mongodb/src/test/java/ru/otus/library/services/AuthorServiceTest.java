package ru.otus.library.services;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.repositories.AuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(AuthorService.class)
public class AuthorServiceTest {
    @Autowired
    private AuthorService service;
    @MockBean
    private AuthorRepository repository;

    private static final ObjectId ID = new ObjectId();
    private static final Author AUTHOR = new Author(ID, "test");

    @Test
    public void shouldReturnAllAuthors() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(AUTHOR));
        List<Author> actual = service.getAll();
        assertThat(actual).hasSize(1);
    }

    @Test
    public void shouldReturnAuthorById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(AUTHOR));
        Author actual = service.getById(ID);
        assertThat(actual).isNotNull();
    }

    @Test
    public void shouldDeleteAuthorById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(AUTHOR));
        String actual = service.deleteById(ID);
        assertThat(actual).isEqualTo(AUTHOR + " deleted");
    }

    @Test
    public void shouldUpdateAuthorFullName() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(AUTHOR));
        Author expected = new Author(AUTHOR.getId(), AUTHOR.getFullName());
        String actual = service.update("update", ID);
        assertThat(actual).isNotEqualTo(expected + " update");
    }

}
