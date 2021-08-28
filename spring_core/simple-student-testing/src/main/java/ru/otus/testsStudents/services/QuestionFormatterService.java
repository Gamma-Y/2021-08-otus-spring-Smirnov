package ru.otus.testsStudents.services;

import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.util.List;

public class QuestionFormatterService {

    public String questionFormat(Question question) {
        return formatter(question);
    }

    public String questionsFormat(List<Question> questions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Question question : questions) {
            stringBuilder.append(questionFormat(question));
        }

        return stringBuilder.toString();
    }

    private String formatter(Question question) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.getDescription()).append(":").append("\n");
        List<Answer> answers = question.getAnswers();

        for (int i = 0; i < answers.size(); ) {
            Answer answer = answers.get(i);
            stringBuilder.append(String.format("\t%2d) ", ++i)).append(answer.getDescription()).append("\n");
        }

        return stringBuilder.toString();
    }


}
