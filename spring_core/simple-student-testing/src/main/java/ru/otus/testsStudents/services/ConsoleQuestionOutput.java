package ru.otus.testsStudents.services;

import org.springframework.stereotype.Component;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.util.List;

@Component
public class ConsoleQuestionOutput extends TextOutput {

    @Override
    String format(Question question) {
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
