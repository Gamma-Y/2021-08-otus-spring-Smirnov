package ru.otus.testsStudents.services.console;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleReaderService implements Reader {
    private static final Scanner console = new Scanner(System.in);

    @Override
    public int getInt() {
        int answerIndex = -1;
        if (console.hasNextLine()) {
            answerIndex = console.nextInt() - 1;
        }
        return answerIndex;
    }

    @Override
    public String getString() {
        String str = "";
        if (console.hasNextLine()) {
            str = console.nextLine();
        }
        return str;
    }
}
