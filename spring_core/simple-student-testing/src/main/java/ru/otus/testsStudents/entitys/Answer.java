package ru.otus.testsStudents.entitys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Answer {
    private final String description;

    public Answer(String description) {
        this.description = description;
    }


}
