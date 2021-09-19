package ru.otus.testsStudents.services.resources.readers;

import java.util.List;

public interface ResourceReader {
    List<String[]> fileReader(String fileName);
}
