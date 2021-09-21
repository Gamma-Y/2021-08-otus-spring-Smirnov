package ru.otus.testsStudents.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.testsStudents.entitys.Question;
import ru.otus.testsStudents.entitys.Student;
import ru.otus.testsStudents.services.console.TextOutput;
import ru.otus.testsStudents.services.localization.Localizer;
import ru.otus.testsStudents.services.resources.loaders.QuestionLoader;
import ru.otus.testsStudents.services.console.Reader;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class TestService implements StudentTest {
    private final QuestionLoader loader;
    private final Reader reader;
    private final TextOutput consoleOutput;
    private final Localizer localizer;
    private final double percentCorrect;
    private List<Question> questions = new ArrayList<>();

    public TestService(QuestionLoader loader, Reader reader, TextOutput consoleOutput, Localizer localizer, @Value("${test.passed.percent}") double percentCorrect) {
        this.loader = loader;
        this.reader = reader;
        this.consoleOutput = consoleOutput;
        this.localizer = localizer;
        this.percentCorrect = percentCorrect;
    }

    @Override
    @ShellMethod(key = "start-test", value = "Launches the history test")
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
        System.out.print(localizer.getLocalizedMessage("test.student.init", new String[]{}));
        String[] nameParts = reader.getString().split(" ");
        return new Student(nameParts[0], nameParts[1]);
    }

    private void endTest(Student student, int questionCount, int correctAnswerCount) {
        double percent = ((double) correctAnswerCount / questionCount) * 100;
        if (percent > percentCorrect) {
            System.out.print(localizer.getLocalizedMessage("test.passed", new String[]{student.getName(), student.getLastName(), String.format("%.2f", percent), String.valueOf(percentCorrect)}));
        } else {
            System.out.print(localizer.getLocalizedMessage("test.failed", new String[]{student.getName(), student.getLastName(), String.format("%.2f", percent), String.valueOf(percentCorrect)}));
        }
    }

}
