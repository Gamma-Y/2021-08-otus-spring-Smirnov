package ru.otus.testsStudents.services.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;
import ru.otus.testsStudents.services.localization.LocalizationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест форматера вопросов")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class ConsoleQuestionOutputTest {
    @MockBean
    private LocalizationService localizer;
    @Autowired
    private ConsoleQuestionOutput mockConsoleQuestionOutput;

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
