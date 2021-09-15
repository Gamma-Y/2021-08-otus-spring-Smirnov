package ru.otus.testsStudents.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class LoaderFromResources implements QuestionLoader {
    private static final String CSV_SEPARATOR = ";";
    private final ResourceReader localResourceReader;
    private final String questionFileName;
    private final String answerFileName;

    public LoaderFromResources(@Value("${file.question}") String questionFileName, @Value("${file.answer}") String answerFileName, ResourceReader localResourceReader) {
        this.questionFileName = questionFileName;
        this.answerFileName = answerFileName;
        this.localResourceReader = localResourceReader;
    }

    @Override
    public List<Question> getQuestion() {
        List<String[]> fileLines = localResourceReader.fileReader(questionFileName);
        Map<String, Question> questions = new HashMap<>();
        for (String[] questionWithId : fileLines) {
            String[] parts = questionWithId[0].split(CSV_SEPARATOR);
            questions.put(parts[0], new Question(parts[1]));
        }

        findAnswerForQuestions(questions);

        return new ArrayList<Question>(questions.values());
    }

    private void findAnswerForQuestions(Map<String, Question> questionMap) {
        List<String[]> fileLines = localResourceReader.fileReader(answerFileName);
        for (String[] answerWithId : fileLines) {
            String[] parts = answerWithId[0].split(CSV_SEPARATOR);
            Question question = questionMap.get(parts[0]);
            question.addAnswer(new Answer(parts[1], Boolean.parseBoolean(parts[2])));
        }

    }


}
