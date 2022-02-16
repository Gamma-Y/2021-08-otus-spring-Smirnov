package ru.otus.etl.service;

import ru.otus.etl.batch.dvo.BookDvo;
import ru.otus.etl.database.mongo.model.BookDocument;

public interface DvoService {
    BookDvo createBookDvo(BookDocument document);
}
