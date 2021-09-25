package ru.otus.library.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormatterService {
    public String formatList(List list) {
        StringBuilder builder = new StringBuilder();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            builder.append(list.get(i).toString() + "\n");
        }
        return builder.toString();
    }

}
