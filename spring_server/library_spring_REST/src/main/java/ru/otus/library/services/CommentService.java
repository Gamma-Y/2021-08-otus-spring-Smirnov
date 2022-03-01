package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.controllers.dto.CommentDTO;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;
import ru.otus.library.database.repositories.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final BookRepository bookRepository;

    public List<Comment> getAll() {
        List<Comment> comments = repository.findAll();
        return comments;
    }

    public Comment getById(long id) {
        Comment comment = repository.findById(id).get();
        return comment;
    }

    @HystrixCommand(fallbackMethod = "returnStatus", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public boolean deleteById(long id) {
        Comment comment = repository.findById(id).get();
        repository.delete(comment);
        return true;
    }

    public Comment update(String text, long id) {
        Comment comment = repository.findById(id).get();
        comment.setText(text);
        repository.save(comment);
        return comment;
    }

    public void save(String text, long bookId) {
        Book book = bookRepository.findById(bookId).get();
        repository.save(new Comment(text, System.currentTimeMillis()));
    }

    @HystrixCommand(fallbackMethod = "buildFallbackComment", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    public CommentDTO save(CommentDTO commentDTO) {
        Book book = bookRepository.findById(commentDTO.getBookId()).get();
        return commentToDto(repository.save(new Comment(commentDTO.getText(), commentDTO.getTimeStamp(), book)));
    }

    private CommentDTO commentToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setTimeStamp(comment.getDateTime());
        return commentDTO;
    }

    public CommentDTO buildFallbackComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBookId(-1);
        commentDTO.setText("N/A");
        commentDTO.setId(-1);
        commentDTO.setTimeStamp(-1);

        return commentDTO;
    }

    public CommentDTO buildFallbackComment(CommentDTO oldCommentDTO) {
        CommentDTO commentDTO = buildFallbackComment();
        return commentDTO;
    }

    public boolean returnStatus(long... id) {
        return false;
    }
}
