package ru.otus.testsStudents.services.console;

import ru.otus.testsStudents.entitys.Question;

public abstract class TextOutput {
    public void printQuestion(Question question){
        System.out.println(format(question));
    };
    protected abstract String format(Question question);
}
