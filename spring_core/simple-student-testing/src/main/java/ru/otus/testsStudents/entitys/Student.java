package ru.otus.testsStudents.entitys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Student {
    private final String name;
    private final String lastName;
    private int numberCorrectAnswers = 0;

    public Student(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
}
