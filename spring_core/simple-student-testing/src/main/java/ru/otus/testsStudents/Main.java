package ru.otus.testsStudents;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.testsStudents.services.QuestionLoaderService;
import ru.otus.testsStudents.services.TestService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService service = context.getBean(TestService.class);
        service.loadQuestion();
        service.printAllQuestion();

        context.close();
    }
}
