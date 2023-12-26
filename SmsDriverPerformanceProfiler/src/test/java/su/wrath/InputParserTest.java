package su.wrath;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



public class InputParserTest {
    InputParser parser = new InputParser();

    @Test
    public void inputIsTooShort() {
        String inputShortString = "123456789";
        ParsingResult result = parser.processString(inputShortString);
        assertEquals("Некорректный формат входных данных",result.getErrorDescription());

        inputShortString = "1 3 5 789";
        ParsingResult result2 = parser.processString(inputShortString);
        assertEquals("Некорректный формат входных данных",result2.getErrorDescription());
    }

    @Test
    public void inputHasLessThanNeededArguments() {
        String inputLowArgs = "     mta ав141х 1     "; // разбавили пробелами чтобы пролезало по длине
        ParsingResult result = parser.processString(inputLowArgs);
        assertEquals("Некорректное число входных аргументов",result.getErrorDescription());
    }
    @Test
    public void inputHasTooManyArguments() {
        String inputManyArgs = "mta ав141х 1 1 1";
        ParsingResult result = parser.processString(inputManyArgs);
        assertEquals("Некорректное число входных аргументов",result.getErrorDescription());
    }
    @Test
    public void wrongMsgPrefix() {
        String wrongPrefix = "f16 ав141х 1 1";
        ParsingResult result = parser.processString(wrongPrefix);
        assertEquals("Неверно указан префикс",result.getErrorDescription());
    }
    @Test
    public void wrongTransportNum() {
        String wrongTransNumb = "mta а1141х 1 1";
        ParsingResult result = parser.processString(wrongTransNumb);
        assertEquals("Неверно указан номер ТС",result.getErrorDescription());
    }

    @Test
    public void nonIntCategory() {
        String nonIntCategoryInput = "mta ав141х Z 1";
        ParsingResult result = parser.processString(nonIntCategoryInput);
        assertEquals("Неверно указан тип оценки",result.getErrorDescription());
    }
    @Test
    public void zeroCategory() {
        String zeroCategoryInput = "mta ав141х 0 1";
        ParsingResult result = parser.processString(zeroCategoryInput);
        assertEquals("Неверно указан тип оценки",result.getErrorDescription());
    }

    @Test
    public void negativeCategory() {
        String negativeCategoryInput = "mta ав141х -2 1";
        ParsingResult result = parser.processString(negativeCategoryInput);
        assertEquals("Неверно указан тип оценки",result.getErrorDescription());
    }
    @Test
    public void tooLargeCategory() {
        String largeCategoryInput = "mta ав141х 6 1";
        ParsingResult result = parser.processString(largeCategoryInput);
        assertEquals("Неверно указан тип оценки",result.getErrorDescription());
    }
    @Test
    public void nonIntReview() {
        String nonIntReviewInput = "mta ав141х 1 V";
        ParsingResult result = parser.processString(nonIntReviewInput);
        assertEquals("Неверно указана оценка",result.getErrorDescription());
    }
    @Test
    public void zeroAsReview() {
        String zeroAsReviewInput = "mta ав141х 1 0";
        ParsingResult result = parser.processString(zeroAsReviewInput);
        assertEquals("Неверно указана оценка",result.getErrorDescription());
    }
    @Test
    public void negativeAsReview() {
        String negativeReviewInput = "mta ав141х 1 -1";
        ParsingResult result = parser.processString(negativeReviewInput);
        assertEquals("Неверно указана оценка",result.getErrorDescription());
    }
    @Test
    public void highReview() {
        String highReviewInput = "mta ав141х 1 9";
        ParsingResult result = parser.processString(highReviewInput);
        assertEquals("Неверно указана оценка",result.getErrorDescription());
    }

    @Test
    public void correctInputTest() {
        String validInput = "mta ав141х 3 2";
        ParsingResult result = parser.processString(validInput);
        assertEquals("Обработка прошла успешно",result.getErrorDescription());
    }
    @Test
    public void correctInputTestWithSpaces() {
        String validInput = "  mta  ав141х  3   2  ";
        ParsingResult result = parser.processString(validInput);
        assertEquals("Обработка прошла успешно",result.getErrorDescription());
    }
}
