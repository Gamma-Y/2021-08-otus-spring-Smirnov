package ru.otus.testsStudents.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ConsoleQuestionOutput extends TextOutput {
    private final MessageSource message;

    @Override
    String format(Question question) {
        StringBuilder stringBuilder = new StringBuilder();
        String localizationQuestion = message.getMessage(question.getLink(), new String[]{}, Locale.forLanguageTag("ru"));
        stringBuilder.append(localizationQuestion).append(":").append("\n");
        List<Answer> answers = question.getAnswers();

        for (int i = 0; i < answers.size(); ) {
            Answer answer = answers.get(i);
            String localizationAnswer = message.getMessage(answer.getLink(), new String[]{}, Locale.forLanguageTag("ru"));
            stringBuilder.append(String.format("\t%2d) ", ++i)).append(localizationAnswer).append("\n");
        }

        return stringBuilder.toString();
    }
}
