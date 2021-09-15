package ru.otus.testsStudents.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationService implements Localizer {
    private final MessageSource messageLocalizer;
    private Locale locale;

    public LocalizationService(MessageSource messageLocalizer, @Value("default.language") String language, @Value("default.country") String country) {
        this.messageLocalizer = messageLocalizer;
        this.locale = new Locale(language, country);
    }

    @Override
    public String getLocalizedMessage(String messageId, String[] args) {
        return messageLocalizer.getMessage(messageId, args, locale);
    }

    @Override
    public void changeLanguage(String language, String country) {
        this.locale = new Locale(language, country);
    }

}
