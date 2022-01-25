package ru.otus.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.controllers.dto.AuthorDTO;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.controllers.dto.CommentDTO;
import ru.otus.library.controllers.dto.GenreDTO;
import ru.otus.library.services.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    private static final String AUTHOR_NAME = "author";
    private static final String GENRE_TITLE = "genre";
    private static final String BOOK_NAME = "test";
    private static final long BOOK_ID = 1L;
    private static final long COMMENT_ID = 1L;
    private static final BookDTO BOOK_DTO = new BookDTO();
    private static final CommentDTO COMMENT_DTO = new CommentDTO();
    private static final AuthorDTO AUTHOR_DTO = new AuthorDTO();
    private static final GenreDTO GENRE_DTO = new GenreDTO();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService service;
    @MockBean
    private CommentService commentService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private UserService userService;


    @BeforeAll
    public static void before() {
        BOOK_DTO.setGenres(List.of(GENRE_TITLE));
        BOOK_DTO.setAuthors(List.of(AUTHOR_NAME));
        BOOK_DTO.setId(BOOK_ID);
        BOOK_DTO.setName(BOOK_NAME);
        COMMENT_DTO.setTimeStamp(System.currentTimeMillis());
        COMMENT_DTO.setId(COMMENT_ID);
        COMMENT_DTO.setBookId(BOOK_ID);
        COMMENT_DTO.setText("test");
        AUTHOR_DTO.setFullName("test");
        AUTHOR_DTO.setBookId(BOOK_ID);
        AUTHOR_DTO.setId(1L);
        GENRE_DTO.setTitle("test");
        GENRE_DTO.setBookId(BOOK_ID);
        GENRE_DTO.setId(1L);
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnCorrectBookList() throws Exception {
        when(service.getAllHowDTO()).thenReturn(List.of(BOOK_DTO));
        this.mockMvc.perform(get("/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(BOOK_ID))
                .andExpect(jsonPath("$[0].authors[0]").value(AUTHOR_NAME))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUser() throws Exception {
        this.mockMvc.perform(post("/delete/book").param("id", "1"))
                .andExpect(status().is3xxRedirection());
        verify(service, times(0)).deleteById(anyLong());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void shouldDeleteBook() throws Exception {
        this.mockMvc.perform(post("/delete/book").param("id", "1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyLong());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryDeleteAuthorFromBook() throws Exception {
        this.mockMvc.perform(post("/delete/book/author")
                        .param("bookId", "1")
                        .param("authorId", "1"))
                .andExpect(status().is3xxRedirection());
        verify(service, times(0)).deleteAuthorFromBook(anyLong(), anyLong());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void shouldDeleteAuthorFromBook() throws Exception {
        this.mockMvc.perform(post("/delete/book/author")
                        .param("bookId", "1")
                        .param("authorId", "1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteAuthorFromBook(anyLong(), anyLong());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryDeleteGenreFromBook() throws Exception {
        this.mockMvc.perform(post("/delete/book/genre")
                        .param("bookId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is3xxRedirection());
        verify(service, times(0)).deleteGenreFromBook(anyLong(), anyLong());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void shouldDeleteGenreFromBook() throws Exception {
        this.mockMvc.perform(post("/delete/book/genre")
                        .param("bookId", "1")
                        .param("genreId", "1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteGenreFromBook(anyLong(), anyLong());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryDeleteCommentFromBook() throws Exception {
        this.mockMvc.perform(post("/delete/book/comment")
                        .param("commentId", "1"))
                .andExpect(status().is3xxRedirection());
        verify(commentService, times(0)).deleteById(anyLong());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldDeleteCommentFromBook() throws Exception {
        this.mockMvc.perform(post("/delete/book/comment")
                        .param("commentId", "1"))
                .andExpect(status().isOk());
        verify(commentService, times(1)).deleteById(anyLong());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryAddCommentForBook() throws Exception {
        when(commentService.save(COMMENT_DTO)).thenReturn(COMMENT_DTO);
        this.mockMvc.perform(post("/add/book/comment")
                        .content(objectMapper.writeValueAsString(COMMENT_DTO)))
                .andExpect(status().is3xxRedirection());
        verify(commentService, times(0)).save(any());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldAddCommentForBook() throws Exception {
        when(commentService.save(COMMENT_DTO)).thenReturn(COMMENT_DTO);
        this.mockMvc.perform(post("/add/book/comment")
                        .content(objectMapper.writeValueAsString(COMMENT_DTO)))
                .andExpect(status().isOk());
        verify(commentService, times(1)).save(any());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryAddAuthorForBook() throws Exception {
        when(authorService.addBookToAuthor(AUTHOR_DTO)).thenReturn(AUTHOR_DTO);
        this.mockMvc.perform(post("/add/book/author")
                        .content(objectMapper.writeValueAsString(AUTHOR_DTO)))
                .andExpect(status().is3xxRedirection());
        verify(authorService, times(0)).addBookToAuthor(any());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void shouldAddAuthorForBook() throws Exception {
        when(authorService.addBookToAuthor(AUTHOR_DTO)).thenReturn(AUTHOR_DTO);
        this.mockMvc.perform(post("/add/book/author")
                        .content(objectMapper.writeValueAsString(AUTHOR_DTO)))
                .andExpect(status().isOk());
        verify(authorService, times(1)).addBookToAuthor(any());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryAddGenreForBook() throws Exception {
        when(genreService.addGenreToBook(GENRE_DTO)).thenReturn(GENRE_DTO);
        this.mockMvc.perform(post("/add/book/genre")
                        .content(objectMapper.writeValueAsString(GENRE_DTO)))
                .andExpect(status().is3xxRedirection());
        verify(genreService, times(0)).addGenreToBook(any());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void shouldAddGenreForBook() throws Exception {
        when(genreService.addGenreToBook(GENRE_DTO)).thenReturn(GENRE_DTO);
        this.mockMvc.perform(post("/add/book/genre")
                        .content(objectMapper.writeValueAsString(GENRE_DTO)))
                .andExpect(status().isOk());
        verify(genreService, times(1)).addGenreToBook(any());
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectForAnonymousUserWhenTryUpdateBook() throws Exception {
        String testStr = "update";
        BOOK_DTO.setName(testStr);
        when(service.updateBook(BOOK_DTO)).thenReturn(BOOK_DTO);
        this.mockMvc.perform(post("/update/book")
                        .content(objectMapper.writeValueAsString(BOOK_DTO)))
                .andExpect(status().is3xxRedirection());
        verify(service, times(0)).updateBook(any());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void shouldUpdateBook() throws Exception {
        String testStr = "update";
        BOOK_DTO.setName(testStr);
        when(service.updateBook(BOOK_DTO)).thenReturn(BOOK_DTO);
        this.mockMvc.perform(post("/update/book")
                        .content(objectMapper.writeValueAsString(BOOK_DTO)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateBook(any());
    }


}
