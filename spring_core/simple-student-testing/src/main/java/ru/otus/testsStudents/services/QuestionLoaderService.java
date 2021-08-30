package ru.otus.testsStudents.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class QuestionLoaderService {
    private static final String CSV_SEPARATOR = ";";
    private String questionFileName;
    private String answerFileName;

    public ArrayList<Question> createQuestions() {
        List<String[]> fileLines = fileReader(questionFileName);
        Map<String, Question> questions = new HashMap<>();
        for (String[] questionWithId : fileLines) {
            String[] parts = questionWithId[0].split(CSV_SEPARATOR);
            questions.put(parts[0], new Question(parts[1]));
        }

        findAnswerForQuestions(questions);

        return new ArrayList<Question>(questions.values());
    }

    private void findAnswerForQuestions(Map<String, Question> questionMap) {
        List<String[]> fileLines = fileReader(answerFileName);
        for (String[] answerWithId : fileLines) {
            String[] parts = answerWithId[0].split(CSV_SEPARATOR);
            Question question = questionMap.get(parts[0]);
            question.addAnswer(new Answer(parts[1]));
        }

    }

    private List<String[]> fileReader(String fileName) {
        List<String[]> fileLines = null;
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            fileLines = reader.readAll();

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return fileLines;
    }


    public String getQuestionFileName() {
        return questionFileName;
    }

    public void setQuestionFileName(String questionFileName) {
        this.questionFileName = questionFileName;
    }

    public String getAnswerFileName() {
        return answerFileName;
    }

    public void setAnswerFileName(String answerFileName) {
        this.answerFileName = answerFileName;
    }
}
