package ru.otus.library.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("тест репозитория работающего с комментариями")
@DataJpaTest
@Import(CommentRepositoryJPA.class)
public class CommentRepositoryJPATest {
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final long COMMENT_ID = 1;
    private static final String COMMENT_TEXT = "test";
    private static final long COMMENT_TIME  = System.currentTimeMillis();
    @Autowired
    private CommentRepositoryJPA repository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен найти комментарий по id")
    @Test
    public void shouldFindExpectedCommentById() {
        Comment actual = repository.findById(COMMENT_ID).get();
        Comment expected = em.find(Comment.class, COMMENT_ID);
        assertEquals(expected, actual);
    }

    @DisplayName("должен найти все комментарии")
    @Test
    public void shouldFindAllExpectedComment() {
        List<Comment> actual = repository.findAll();
        assertThat(actual).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS);
    }

    @DisplayName("должен сохранить новый комментарий")
    @Test
    public void shouldSaveNewComment() {
        Book book = em.find(Book.class, 1l);
        Comment comment = new Comment(COMMENT_TEXT, COMMENT_TIME, book);
        comment = repository.save(comment);
        Comment actual = em.find(Comment.class, comment.getId());
        assertThat(actual).isNotNull().isEqualTo(comment);
    }

    @DisplayName("должен удалить комментарий")
    @Test
    public void shouldRemoveComment() {
        Book book = em.find(Book.class, 1l);
        Comment comment = new Comment(COMMENT_TEXT, COMMENT_TIME, book);
        comment = em.persist(comment);
        repository.deleteById(comment.getId());
        em.flush();
        List<Comment> actual = em.getEntityManager().createQuery("select c from Comment c", Comment.class).getResultList();
        assertThat(actual).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS);
    }

}
