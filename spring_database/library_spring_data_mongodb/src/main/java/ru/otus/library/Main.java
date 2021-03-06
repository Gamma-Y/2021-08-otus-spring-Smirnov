package ru.otus.library;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongock
@SpringBootApplication
@EnableMongoRepositories
public class Main {
    public static void main(String[] args) {
       SpringApplication.run(Main.class, args);
    }
}
