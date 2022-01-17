package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Mono;
import ru.otus.library.database.entities.Author;
import ru.otus.library.database.entities.Book;
import ru.otus.library.database.entities.Comment;
import ru.otus.library.database.repositories.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        BookRepository repository = context.getBean(BookRepository.class);

        Author author1 = new Author("Кэтти Сьерра");
        Author author2 = new Author("Роберт Мартин Сесил");
        Author author3 = new Author("Клейсон Джорж Самюэль");
        Comment comment1 = new Comment(System.currentTimeMillis(), "comment1");
        Comment comment2 = new Comment(System.currentTimeMillis(), "comment2");
        Comment comment3 = new Comment(System.currentTimeMillis(), "comment3");
        Comment comment4 = new Comment(System.currentTimeMillis(), "comment4");
        Comment comment5 = new Comment(System.currentTimeMillis(), "comment5");
        Comment comment6 = new Comment(System.currentTimeMillis(), "comment6");

        repository.insert(Arrays.asList(
                new Book("Head First Java, Изучаем Java",
                        List.of("Программирование"),
                        List.of(comment1, comment2),
                        List.of(author1, author2)),
                new Book("Чистый код. Создание, анализ и рефакторинг",
                        List.of("Программирование"),
                        List.of(comment3),
                        List.of(author2)),
                new Book("Самый богатый человек в Вавилоне",
                        List.of("Финансы"),
                        List.of(comment4, comment5, comment6),
                        List.of(author1, author3))
        )).subscribe();
//        String b = Objects.requireNonNull(repository.findAll().elementAt(0).block()).getId();
//        repository.findAll()
//                .collectMap(
//                        book -> book.getId(),
//                        book -> book.getName())
//
//                .subscribe(System.out::println);

//        repository.findById(b)
//                .doOnNext(book -> book.getGeneris().add("dasd"))
//                .flatMap(repository::save)





//        .subscribe(System.out::println);


    }
}
