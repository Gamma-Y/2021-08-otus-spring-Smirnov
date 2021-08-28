package ru.otus.testsStudents.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.testsStudents.entitys.Answer;
import ru.otus.testsStudents.entitys.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест форматера вопросов")
class QuestionFormatterServiceTest {

    QuestionFormatterService service = new QuestionFormatterService();


    @Test
    void shouldReturnCorrectFormatterQuestionWithAnswer() {
        String expectedFormatterQuestionWithAnswer = "Test question:\n\t 1) Test answer\n";
        Question question = new Question("Test question");
        Answer answer = new Answer("Test answer");
        question.addAnswer(answer);
        String actualFormatterQuestionWithAnswer = service.questionFormat(question);

        assertEquals(expectedFormatterQuestionWithAnswer, actualFormatterQuestionWithAnswer);

    }

}
