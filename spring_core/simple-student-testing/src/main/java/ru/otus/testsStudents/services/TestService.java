package ru.otus.testsStudents.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.testsStudents.entitys.Question;
import ru.otus.testsStudents.entitys.Student;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TestService {
    private final QuestionLoader loader;
    private final Reader reader;
    private final TextOutput consoleOutput;
    private final MessageSource message;
    private final int percentCorrect;
    private List<Question> questions = new ArrayList<>();

    public TestService(QuestionLoader loader, Reader reader, TextOutput consoleOutput, MessageSource message, @Value("${test.passed.percent}") int percentCorrect) {
        this.loader = loader;
        this.reader = reader;
        this.consoleOutput = consoleOutput;
        this.message = message;
        this.percentCorrect = percentCorrect;
    }

    public void start() {
        int questionCount = 0;
        int correctAnswerCount = 0;
        Student student = createStudent();
        for (Question question : questions) {
            questionCount++;
            consoleOutput.printQuestion(question);
            if (question.isCorrectAnswer(reader.getInt())) correctAnswerCount++;
        }
        endTest(student, questionCount, correctAnswerCount);

    }

    @PostConstruct
    private void init() {
        questions.addAll(getQuestions());

    }

    private List<Question> getQuestions() {
        return loader.getQuestion();
    }

    private Student createStudent() {
        System.out.print(message.getMessage("test.student.init", new String[]{}, Locale.forLanguageTag("ru")));
        String[] nameParts = reader.getString().split(" ");
        return new Student(nameParts[0], nameParts[1]);
    }

    private void endTest(Student student, int questionCount, int correctAnswerCount) {
        int percent = questionCount / correctAnswerCount * 100;
        if (percent > percentCorrect) {
            System.out.print(message.getMessage("test.passed", new String[]{student.getName(), student.getLastName(), String.valueOf(percent), String.valueOf(percentCorrect)}, Locale.forLanguageTag("ru")));
        } else {
            System.out.print(message.getMessage("test.failed", new String[]{student.getName(), student.getLastName(), String.valueOf(percent), String.valueOf(percentCorrect)}, Locale.forLanguageTag("ru")));
        }
    }

}
