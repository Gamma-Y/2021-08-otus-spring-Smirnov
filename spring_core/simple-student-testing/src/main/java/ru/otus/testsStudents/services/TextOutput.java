package ru.otus.testsStudents.services;

import ru.otus.testsStudents.entitys.Question;

public abstract class TextOutput {
    protected void printQuestion(Question question){
        System.out.println(format(question));
    };
    protected abstract String format(Question question);
}
