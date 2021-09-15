package ru.otus.testsStudents.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест загрузчика вопросов")
public class LoaderFromResourcesTest {
    private final ResourceReader mockResourceReader = Mockito.mock(LocalResourceReader.class);

    @Test
    void shouldReturnQuestionWithAnswerFromResourcesFile() {
        String answerFileName = "answerFileTest";
        String questionFileName = "questionFileTest";
        QuestionLoader loader = new LoaderFromResources(questionFileName, answerFileName, mockResourceReader);
        Mockito.when(mockResourceReader.fileReader(questionFileName)).thenReturn(Collections.singletonList(new String[]{"1;test"}));
        Mockito.when(mockResourceReader.fileReader(answerFileName)).thenReturn(Collections.singletonList(new String[]{"1;test;true"}));

        Question question = new Question("test");
        question.addAnswer(new Answer("test", true));

        List<Question> actual = loader.getQuestion();
        List<Question> expected = new ArrayList<>() {
            {
                add(question);
            }
        };

        assertEquals(expected, actual);
        assertEquals(question.getAnswers().size(), actual.get(0).getAnswers().size());
        assertEquals(question.getAnswers(), actual.get(0).getAnswers());
    }

}
