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
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(BookService.class)
public class BookServiceTest {
    @Autowired
    private BookService service;
    @MockBean
    private BookRepository repository;
    @MockBean
    private SequenceService sequenceService;

    private static final ObjectId ID = new ObjectId();
    private static final Comment COMMENT = new Comment(1L, "test", System.currentTimeMillis());
    private static final Author AUTHOR = new Author(ID, "test");
    private static final Book BOOK = new Book(ID, "test", List.of("test"), List.of(COMMENT), List.of(AUTHOR));

    @Test
    public void shouldReturnAllBooks() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(BOOK));
        List<Book> actual = service.getAll();
        assertThat(actual).hasSize(1);
    }

    @Test
    public void shouldReturnBookById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(BOOK));
        Book actual = service.getById(ID);
        assertThat(actual).isNotNull();
    }

    @Test
    public void shouldDeleteBookById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(BOOK));
        String actual = service.deleteById(ID);
        assertThat(actual).isEqualTo(BOOK + " deleted");
    }

    @Test
    public void shouldUpdateBookName() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(BOOK));
        Book expected = new Book(ID, BOOK.getName(), List.of(BOOK.getGeneris().get(0)), List.of(COMMENT), List.of(AUTHOR));
        String actual = service.update("update", ID);
        assertThat(actual).isNotEqualTo(expected + " update");
    }

    @Test
    public void shouldReturnBookComments() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(BOOK));
        List<Comment> actual = service.getBookCommentsByBookId(ID);
        assertThat(actual).isEqualTo(List.of(COMMENT));
    }

    @Test
    public void shouldReturnBookByGenre() {
        Mockito.when(repository.findByGeneris(BOOK.getGeneris().get(0))).thenReturn(List.of(BOOK));
        List<Book> actual = service.getBooksByGenre(BOOK.getGeneris().get(0));
        assertThat(actual).isEqualTo(List.of(BOOK));
    }


}
