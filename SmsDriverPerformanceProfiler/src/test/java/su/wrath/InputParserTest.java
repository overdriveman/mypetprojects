package su.wrath;

import static org.junit.jupiter.api.Assertions.*;
import static su.wrath.InputParser.processString;

import org.junit.jupiter.api.Test;

public class InputParserTest {

    @Test
    public void inputIsTooShort() {
        String inputShortString = "123456789";
        ParsingResult result = processString(inputShortString);
        assertEquals("Некорректный формат входных данных", result.getErrorDescription(), "Короткий ввод вызывает ошибку");

        inputShortString = "1 3 5 789";
        ParsingResult result2 = processString(inputShortString);
        assertEquals("Некорректный формат входных данных", result2.getErrorDescription(), "Некорректный формат с ошибкой");
    }

    @Test
    public void inputHasLessThanNeededArguments() {
        String inputLowArgs = "     mta ав141х 1     "; // разбавили пробелами чтобы пролезало по длине
        ParsingResult result = processString(inputLowArgs);
        assertEquals("Некорректное число входных аргументов", result.getErrorDescription(), "Недостаточно аргументов это ошибка");
    }

    @Test
    public void inputHasTooManyArguments() {
        String inputManyArgs = "mta ав141х 1 1 1";
        ParsingResult result = processString(inputManyArgs);
        assertEquals("Некорректное число входных аргументов", result.getErrorDescription(), "Много аргументов это ошибка");
    }

    @Test
    public void wrongMsgPrefix() {
        String wrongPrefix = "f16 ав141х 1 1";
        ParsingResult result = processString(wrongPrefix);
        assertEquals("Неверно указан префикс", result.getErrorDescription(), "Неверный префикс это ошибка");
    }

    @Test
    public void wrongTransportNum() {
        String wrongTransNumb = "mta а1141х 1 1";
        ParsingResult result = processString(wrongTransNumb);
        assertEquals("Неверно указан номер ТС", result.getErrorDescription(), "Некорректный номер ТС ошибка");
    }

    @Test
    public void nonIntCategory() {
        String nonIntCategoryInput = "mta ав141х Z 1";
        ParsingResult result = processString(nonIntCategoryInput);
        assertEquals("Неверно указан тип оценки", result.getErrorDescription(), "Некорректная категория оценки ошибка");
    }

    @Test
    public void zeroCategory() {
        String zeroCategoryInput = "mta ав141х 0 1";
        ParsingResult result = processString(zeroCategoryInput);
        assertEquals("Неверно указан тип оценки", result.getErrorDescription(), "Некорректная оценка это ошибка");
    }

    @Test
    public void negativeCategory() {
        String negativeCategoryInput = "mta ав141х -2 1";
        ParsingResult result = processString(negativeCategoryInput);
        assertEquals("Неверно указан тип оценки", result.getErrorDescription(), "Некорректная категория оценки");
    }

    @Test
    public void tooLargeCategory() {
        String largeCategoryInput = "mta ав141х 6 1";
        ParsingResult result = processString(largeCategoryInput);
        assertEquals("Неверно указан тип оценки", result.getErrorDescription(), "Некорректная категория оценки");
    }

    @Test
    public void nonIntReview() {
        String nonIntReviewInput = "mta ав141х 1 V";
        ParsingResult result = processString(nonIntReviewInput);
        assertEquals("Неверно указана оценка", result.getErrorDescription(), "Некорректная оценка это ошибка");
    }

    @Test
    public void zeroAsReview() {
        String zeroAsReviewInput = "mta ав141х 1 0";
        ParsingResult result = processString(zeroAsReviewInput);
        assertEquals("Неверно указана оценка", result.getErrorDescription(), "Некорректная оценка это ошибка");
    }

    @Test
    public void negativeAsReview() {
        String negativeReviewInput = "mta ав141х 1 -1";
        ParsingResult result = processString(negativeReviewInput);
        assertEquals("Неверно указана оценка", result.getErrorDescription(), "Некорректная оценка это ошибка");
    }

    @Test
    public void highReview() {
        String highReviewInput = "mta ав141х 1 9";
        ParsingResult result = processString(highReviewInput);
        assertEquals("Неверно указана оценка", result.getErrorDescription(), "Некорректная оценка это ошибка");
    }

    @Test
    public void correctInputTest() {
        String validInput = "mta ав141х 3 2";
        ParsingResult result = processString(validInput);
        assertEquals("Обработка прошла успешно", result.getErrorDescription(), "Успешная обработка!");
    }

    @Test
    public void correctInputTestWithSpaces() {
        String validInput = "  mta  ав141х  3   2  ";
        ParsingResult result = processString(validInput);
        assertEquals("Обработка прошла успешно", result.getErrorDescription(), "Успешная обработка!");
    }
}
