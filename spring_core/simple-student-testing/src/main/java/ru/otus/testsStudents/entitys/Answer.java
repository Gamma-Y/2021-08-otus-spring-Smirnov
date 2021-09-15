package ru.otus.testsStudents.entitys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Answer {
    private final String link;
    private final boolean isCorrect;
}
