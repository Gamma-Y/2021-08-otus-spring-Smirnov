package ru.otus.testsStudents.entitys;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String description;
    private List<Answer> answers = new ArrayList<>();

    public Question(String description) {
        this.description = description;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public String getDescription() {
        return description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "description='" + description + '\'' +
                ", answers=" + answers +
                '}';
    }
}
