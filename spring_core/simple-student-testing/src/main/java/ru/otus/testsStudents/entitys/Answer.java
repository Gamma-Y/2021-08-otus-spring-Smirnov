package ru.otus.testsStudents.entitys;

public class Answer {
    private final String description;

    public Answer(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "description='" + description + '\'' +
                '}';
    }
}
