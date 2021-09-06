package ru.otus.testsStudents.services;

import ru.otus.testsStudents.entitys.Question;

import java.util.List;

public interface QuestionLoader {
    List<Question> getQuestion();
}
