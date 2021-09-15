package ru.otus.testsStudents.entitys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Question {
    private final String link;
    private List<Answer> answers = new ArrayList<>();

    public Question(String link) {
        this.link = link;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public boolean isCorrectAnswer(int i) {
        return answers.get(i).isCorrect();
    }
}
