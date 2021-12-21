package ru.otus.library.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.entities.Genre;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
public class BookControllerTest {
    private static final Author AUTHOR = new Author(1L, "test");
    private static final Genre GENRE = new Genre(1L, "test");
    private static final Comment COMMENT = new Comment("text", System.currentTimeMillis());
    private static final Book BOOK = new Book("test", List.of(COMMENT), List.of(AUTHOR), List.of(GENRE));
    private static final List<Book> BOOKS = List.of(BOOK);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;
    @MockBean
    private CommentService commentService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;

    @BeforeAll
    public static void before() {
        AUTHOR.setBooks(List.of(BOOK));
        GENRE.setBooks(List.of(BOOK));
        COMMENT.setId(1L);
        COMMENT.setBook(BOOK);
        BOOK.setId(1L);
    }

    @Test
    public void shouldReturnCorrectViewAndBookList() throws Exception {
        when(service.getAll()).thenReturn(BOOKS);
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", BOOKS))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/list/book"))
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", BOOKS))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCorrectViewAndBookForEdit() throws Exception {
        when(service.getById(Mockito.anyLong())).thenReturn(BOOK);
        when(authorService.getAll()).thenReturn(List.of(AUTHOR));
        when(genreService.getAll()).thenReturn(List.of(GENRE));
        this.mockMvc.perform(get("/edit/book").param("id", "1"))
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", BOOK))
                .andExpect(model().attribute("authors", List.of(AUTHOR)))
                .andExpect(model().attribute("generis", List.of(GENRE)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSummonUpdateAndRedirectToCorrectView() throws Exception {
        String id = "1";
        this.mockMvc.perform(post("/edit/book").param("id", id))
                .andExpect(redirectedUrl("/edit/book?id=" + id))
                .andExpect(view().name("redirect:/edit/book?id=" + id))
                .andExpect(status().is3xxRedirection());
        verify(service, times(1)).updateByAttributeMap(Mockito.anyMap());
    }

    @Test
    public void shouldSummonDeleteAndRedirectToCorrectView() throws Exception {
        String id = "1";
        this.mockMvc.perform(get("/delete/book").param("id", id))
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection());
        verify(service, times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void shouldSummonDeleteCommentAndRedirectToCorrectView() throws Exception {
        String id = "1";
        this.mockMvc.perform(get("/delete/book/comment")
                        .param("bookId", id)
                        .param("commentId", id))
                .andExpect(redirectedUrl("/edit/book?id=" + id))
                .andExpect(view().name("redirect:/edit/book?id=" + id))
                .andExpect(status().is3xxRedirection());
        verify(commentService, times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void shouldSummonDeleteGenreAndRedirectToCorrectView() throws Exception {
        String id = "1";

        this.mockMvc.perform(get("/delete/book/genre")
                        .param("bookId", id)
                        .param("genreId", id))
                .andExpect(redirectedUrl("/edit/book?id=" + id))
                .andExpect(view().name("redirect:/edit/book?id=" + id))
                .andExpect(status().is3xxRedirection());
        verify(service, times(1)).deleteGenreFromBook(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    public void shouldSummonDeleteAuthorAndRedirectToCorrectView() throws Exception {
        String id = "1";
        this.mockMvc.perform(get("/delete/book/author")
                        .param("bookId", id)
                        .param("authorId", id))
                .andExpect(redirectedUrl("/edit/book?id=" + id))
                .andExpect(view().name("redirect:/edit/book?id=" + id))
                .andExpect(status().is3xxRedirection());
        verify(service, times(1)).deleteAuthorFromBook(Mockito.anyLong(), Mockito.anyLong());
    }


}
