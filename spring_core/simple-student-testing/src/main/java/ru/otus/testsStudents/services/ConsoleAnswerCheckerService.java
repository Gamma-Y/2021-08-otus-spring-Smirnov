package ru.otus.testsStudents.services;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleAnswerCheckerService implements AnswerChecker{
    private static final Scanner console = new Scanner(System.in);
    @Override
    public int getStudentAnswer() {
        int answerIndex = -1;
        if (console.hasNextLine()){
            answerIndex = console.nextInt() - 1;
        }
        return answerIndex;
    }
}
