package ru.otus.testsStudents.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testsStudents.entitys.Question;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final QuestionLoader loader;
    private final AnswerChecker checker;
    private final TextOutput consoleOutput;
    private List<Question> questions = new ArrayList<>();

    public void start() {
        this.questions.addAll(getQuestions());
        for (Question question : questions) {
            consoleOutput.printQuestion(question);
            System.out.println(checker.getStudentAnswer());
        }
    }

    private List<Question> getQuestions() {
        return loader.getQuestion();
    }

}
