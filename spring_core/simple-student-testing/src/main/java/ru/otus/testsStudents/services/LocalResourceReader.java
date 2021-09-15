package ru.otus.testsStudents.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class LocalResourceReader implements ResourceReader {
    @Override
    public List<String[]> fileReader(String fileName) {
        List<String[]> fileLines = null;
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            fileLines = reader.readAll();

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return fileLines;
    }
}
