package ru.otus.testsStudents.services;

public interface Localizer {
    String getLocalizedMessage(String messageId, String[] args);
    void changeLanguage(String language, String country);
}
