package ru.otus.testsStudents.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест форматера вопросов")
class ConsoleQuestionOutputTest {
    private final Localizer localizer = Mockito.mock(LocalizationService.class);
    private final TextOutput mockConsoleQuestionOutput = new ConsoleQuestionOutput(localizer);

    @Test
    void shouldReturnCorrectFormatterQuestionWithAnswer() {
        String expectedFormatterQuestionWithAnswer = "Test question:\n\t 1) Test answer\n";

        Mockito.when(localizer.getLocalizedMessage(Mockito.anyString(), Mockito.any())).thenReturn("Test question").thenReturn("Test answer");
        Question question = new Question("test link");
        Answer answer = new Answer("test link", true);
        question.addAnswer(answer);
        String actualFormatterQuestionWithAnswer = mockConsoleQuestionOutput.format(question);

        assertEquals(expectedFormatterQuestionWithAnswer, actualFormatterQuestionWithAnswer);

    }

}
