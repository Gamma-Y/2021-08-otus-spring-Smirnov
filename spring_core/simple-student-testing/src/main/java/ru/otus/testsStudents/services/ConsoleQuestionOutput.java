package ru.otus.testsStudents.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsoleQuestionOutput extends TextOutput {
    private final Localizer localizer;

    @Override
    public String format(Question question) {
        StringBuilder stringBuilder = new StringBuilder();
        String localizationQuestion = localizer.getLocalizedMessage(question.getLink(), new String[]{});
        stringBuilder.append(localizationQuestion).append(":").append("\n");
        List<Answer> answers = question.getAnswers();

        for (int i = 0; i < answers.size(); ) {
            Answer answer = answers.get(i);
            String localizationAnswer = localizer.getLocalizedMessage(answer.getLink(), new String[]{});
            stringBuilder.append(String.format("\t%2d) ", ++i)).append(localizationAnswer).append("\n");
        }

        return stringBuilder.toString();
    }
}
