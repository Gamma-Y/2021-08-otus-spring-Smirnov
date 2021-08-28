package ru.otus.testsStudents.services;

import ru.otus.testsStudents.entitys.Question;

import java.util.ArrayList;
import java.util.List;

public class TestService {
    private final QuestionFormatterService questionFormatterService;
    private final QuestionLoaderService questionLoaderService;
    private List<Question> questions = new ArrayList<>();

    public TestService(QuestionFormatterService questionFormatterService, QuestionLoaderService questionLoaderService) {
        this.questionFormatterService = questionFormatterService;
        this.questionLoaderService = questionLoaderService;
    }

    public void loadQuestion() {
        questions = questionLoaderService.createQuestions();
    }

    public void printAllQuestion() {
        System.out.println(questionFormatterService.questionsFormat(questions));
    }

}
