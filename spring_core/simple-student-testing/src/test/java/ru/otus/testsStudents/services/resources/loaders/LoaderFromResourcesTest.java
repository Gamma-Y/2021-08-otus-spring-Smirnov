package ru.otus.testsStudents.services.resources.loaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;
import ru.otus.testsStudents.services.resources.readers.LocalResourceReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест загрузчика вопросов")
@SpringBootTest
@ContextConfiguration(classes = LoaderFromResources.class)
public class LoaderFromResourcesTest {
    @MockBean
    private LocalResourceReader mockResourceReader;

    @Autowired
    private LoaderFromResources loader;

    @Value("${file.question}")
    private String questionFileName;
    @Value("${file.answer}")
    private String answerFileName;


    @Test
    void shouldReturnQuestionWithAnswerFromResourcesFile() {

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
