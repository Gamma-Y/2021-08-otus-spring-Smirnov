package ru.otus.library.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormatterService {
    public String formatListFullInfo(List list) {
        List<Formatter> formatterList = new ArrayList(list);
        StringBuilder builder = new StringBuilder();
        int listSize = formatterList.size();
        for (int i = 0; i < listSize; i++) {
            builder.append(formatterList.get(i).getFullInfo() + "\n");
        }
        return builder.toString();
    }

    public String formatListShortInfo(List list) {
        List<Formatter> formatterList = new ArrayList(list);
        StringBuilder builder = new StringBuilder();
        int listSize = formatterList.size();
        for (int i = 0; i < listSize; i++) {
            builder.append(formatterList.get(i).getShortInfo() + "\n");
        }
        return builder.toString();
    }


}
